#!/bin/bash

#############################################
#       求学大作战 1984组 项目部署脚本         #
#############################################

# functions这个脚本是给/etc/init.d里边的文件使用的。
# https://www.cnblogs.com/image-eye/archive/2011/10/26/2220405.html
. /etc/init.d/functions

# 设置环境变量
# export PATH=$PATH:/bin:/sbin:/usr/sbin

# 设置中文字符
export LANG="zh_CN.UTF-8"

# 前端-前台源码目录
src_html_home="/data/svn/academy-canary-home-html"
# 模块名称
html_home="academy-canary-home-html"
# 前端-后台源码目录
src_html_admin="/data/svn/academy-canary-admin-html"
# 模块名称
html_admin="academy-canary-admin-html"

# 后端-源码目录
src_server_home="/data/svn/academy-canary"
# 后端-web模块
server_web_home="academy-canary-home-web"
server_web_admin="academy-canary-admin-web"
# 后端-service模块
server_service_home="academy-canary-home-service"
server_service_admin="academy-canary-admin-service"
# 后端-core模块
server_core="academy-canary-core"

# 前端-部署根目录
deploy_html_home="/data/webs/academy-canary-home-html"
deploy_html_admin="/data/webs/academy-canary-admin-html"
# Nginx 运行目录
run_html_home=${deploy_html_home}"/run"
run_html_admin=${deploy_html_admin}"/run"

# 后端-部署根目录
deploy_server_home="/data/services/academy-canary-server/home"
deploy_server_admin="/data/services/academy-canary-server/admin"
# SpringBoot运行目录
run_server_home=${deploy_server_home}"/run"
run_server_admin=${deploy_server_admin}"/run"

# 前端-前台备份目录
backups_html_home=${deploy_html_home}"/backups"
# 前端-后台备份目录
backups_html_admin=${deploy_html_admin}"/backups"
# 后端-备份目录
backups_server_home=${deploy_server_home}"/backups"
backups_server_admin=${deploy_server_admin}"/backups"

# 后端-日志目录
logs_server_home=${deploy_server_home}"/logs"
logs_server_admin=${deploy_server_admin}"/logs"

#临时文件名
# Nginx 运行目录
runHtmlName=""
# Springboot运行目录
runServerName=""
# 备份目录
backupsDir=""
# 备份文件名
backupsName=""
# 源码目录
srcName=""

# 判断权限
if [[ "$(whoami)" != "root" ]]; then
    echo "请以root用户运行此脚本" >&2
    exit 1
fi

# 部署
function deployHtml(){
    # 首先同步svn
    updateSVN
    runStatus
    # 判断文件夹是否存在
    if [ -d "$runHtmlName" ]
        then
            echo "前端项目 ${runHtmlName} 不为空"
            echo "开始重新部署"
            sleep 1
            # 将原项目文件备份
            backupHtml
            echo "部署中, 删除原项目 ${runHtmlName} ..."
            rm -rf ${runHtmlName} >/dev/null 2>&1
            runStatus
    else
        echo "前端项目 ${runHtmlName} 为空"
        echo "开始全新部署"${runHtmlName}
        sleep 1
    fi
    echo "从源码 ${srcName} 复制项目至${runHtmlName}"
    sleep 1
    cp -R ${srcName} ${runHtmlName} >/dev/null 2>&1
    runStatus
    echo "重载nginx"
    /data/software-server/nginx/nginx/sbin/nginx -s reload
    sleep 1
    runStatus
}


# SVN 同步
function updateSVN() {
    echo "svn同步 ${srcName} 中..."
    cd ${srcName}
    svn update
}

# 备份
function backupHtml(){
    # 判断备份文件是否存在
    if [ -d "$backupsDir" ]
        then
            echo "前端项目原备份目录"${backupsDir}"存在, 清理上上次的备份文件"
            rm -rf ${backupsDir}/* >/dev/null 2>&1
            sleep 1
            runStatus
    else
        echo "前端项目原备份目录"${backupsDir}"不存在, 备份当前项目"
        # tar 无法再解压文件时创建文件夹
        echo "创建备份文件夹 ${backupsDir}"
        mkdir -R ${backupsDir} >/dev/null 2>&1
        sleep 1
        runStatus
    fi
    echo "从${runHtmlName}"
    echo "至${backupsDir}/${backupsName}-`date +%y%m%d`".tar.gz
    backupsName="${backupsDir}/${backupsName}-`date +%Y%m%d`".tar.gz
    tar -zcvf ${backupsName} ${backupsDir} >/dev/null 2>&1
    echo "备份中..."
    # 等待5s 防止压缩未完成
    sleep 5
    # 判断执行状态
    runStatus
}

# 后端部署
function deployServer(){
    # 同步
    updateSVN
    runStatus
    # 编译
    mvnInstall
    runStatus
    # 判断文件夹是否存在
    if [ -d "$runServerName" ]
        then
            echo "后端项目 ${runServerName} 不为空"
            echo "开始重新部署"
            # 将原项目文件备份
            backupServer
            echo "部署中, 删除原项目 ${runServerName}存在, 清理上上次的备份文件"
            rm -rf ${runServerName}/${backupsName}-1.0-SNAPSHOT.jar >/dev/null 2>&1
            runStatus
    else
        echo "后端项目 ${runServerName} 为空"
        echo "开始全新部署"${runServerName}
        echo "创建文件夹"
        mkdir ${runServerName} >/dev/null 2>&1
        sleep 1
        runStatus
    fi

    runFile=${srcName}/${backupsName}/target/${backupsName}
    echo "从源码目录 $runFile"
    echo "复制项目至${runServerName}/${backupsName}-1.0-SNAPSHOT.jar"
    cp -r ${runFile}-1.0-SNAPSHOT.jar ${runServerName}/${backupsName}-1.0-SNAPSHOT.jar >/dev/null 2>&1
    runStatus
    echo "重载java"
    restServer
    # 自杀 防止影响下次脚本运行
    echo "脚本自杀 防止影响下次脚本运行"
    ps -ed | grep all-deploy.sh $1 | grep -v grep | awk '{print $2}' | xargs kill -9 >/dev/null 2>&1
}
# 备份
function backupServer(){
    # 判断文件夹是否存在
    if [ -d "$backupsDir" ]
        then
            echo "后端项目原备份目录"${backupsDir}"存在, 清理上上次的备份文件"
            echo "rm -rf ${backupsDir}/* >/dev/null 2>&1"
            rm -rf ${backupsDir}/* >/dev/null 2>&1
            runStatus
    else
        echo "前端项目原备份目录"${backupsDir}"不存在, 备份当前运行项目"
        # jar本来就是压缩文件, 移动即可.
        echo "创建备份文件夹 ${backupsDir}"
        mkdir ${backupsDir} >/dev/null 2>&1
        runStatus
    fi
    echo "从${runServerName} 复制"
    echo "至${backupsDir}/${backupsName}-`date +%Y%m%d-%T`".jar
    tempFile="${backupsDir}/${backupsName}-`date +%Y%m%d-%T`".jar
    cp -r ${runServerName}/*.jar ${tempFile}
    echo "备份中..."
    # 等待1s 防止压缩未完成
    sleep 1
    # 判断执行状态
    runStatus
}

# mvn 编译
function mvnInstall() {
    # 先 install core 项目核心
    echo "首先将项目 install 到本地仓库"
    cd ${src_server_home}
    mvn clean install -Dmaven.test.skip=true
    echo
    echo "开始编译WEB服务: ${backupsName} mvn clean package -Dmaven.test.skip=true"
    cd ${srcName}/${backupsName}
    mvn clean package -Dmaven.test.skip=true
}
# 执行状态
function runStatus() {
    if [ $? -ne 0 ]
        then
            action "执行失败.."  /bin/false
            exit 1
    fi
    # 上面命令解压成功为 0
    if [ $? -eq 0 ]
        then
            action "执行成功.."  /bin/true
    fi
}

# 重载server服务
function restServer() {
    # 杀死原进程
    echo "杀死原进程"
    # 这里不能做判断, 当 ${backupsName} 未执行时, 回报语法错误
    ps -ef | grep ${backupsName} | grep -v grep | awk '{print $2}' | xargs kill -9 >/dev/null 2>&1
    echo "启动新项目"
    # runStatus
    # 启动当前进程 在函数内执行会使脚本挂起. 需在结尾加 &
    # setsid java -jar ${runServerName}/${backupsName}-1.0-SNAPSHOT.jar >/dev/null 2>&1
    nohup java -jar ${runServerName}/${backupsName}-1.0-SNAPSHOT.jar --spring.profiles.active=prod >${runLog}/"`date +%Y%m%d%T`".log 2>&1
}

# 将脚本后的第一个单词匹配到$1
case $1 in
    # 如果是1 执行 start()
    1)
    # 部署前端前台
    echo "开始部署前端前台"
    runHtmlName=${run_html_home}
    backupsName=${html_home}
    srcName=${src_html_home}
    backupsDir=${backups_html_home}
    # 调用部署
    deployHtml
    exit 0
;;
    2)
    echo "开始部署前端后台"
    # 部署前端后台
    runHtmlName=${run_html_admin}
    backupsName=${html_admin}
    srcName=${src_html_admin}
    backupsDir=${backups_html_admin}
    # 调用部署
    deployHtml
    exit 0
;;
    3)
    echo "开始部署后端前台"
    runServerName=${run_server_home}
    backupsName=${server_web_home}
    srcName=${src_server_home}
    backupsDir=${backups_server_home}
    runLog=${logs_server_home}
    # 调用部署
    deployServer
    exit 0
;;
    4)
    echo "开始部署后端后台"
    runServerName=${run_server_admin}
    backupsName=${server_web_admin}
    srcName=${src_server_home}
    backupsDir=${backups_server_admin}
    runLog=${logs_server_admin}
    # 调用部署
    deployServer
    exit 0
;;
    5)
    # 部署前端前台
    echo "开始部署前端前台"
    runHtmlName=${run_html_home}
    backupsName=${html_home}
    srcName=${src_html_home}
    backupsDir=${backups_html_home}
    # 调用部署
    deployHtml

    echo "开始部署后端后台"
    runServerName=${run_server_home}
    backupsName=${server_web_home}
    srcName=${src_server_home}
    backupsDir=${backups_server_home}
    # 调用部署
    deployServer

    echo "开始部署前端后台"
    # 部署前端后台
    runHtmlName=${run_html_admin}
    backupsName=${html_admin}
    srcName=${src_html_admin}
    backupsDir=${backups_html_admin}
    # 调用部署
    deployHtml

    echo "开始部署后端后台"
    runServerName=${run_server_admin}
    backupsName=${server_web_admin}
    srcName=${src_server_home}
    backupsDir=${backups_server_admin}
    # 调用部署
    deployServer
    exit 0
;;
    # 如果没有匹配到 输出echo语句
    *)
    echo "请在脚本后添加正确的执行参数:1|2|3|4"
#结束case 注意这个 & , 不然不能后台运行
esac&