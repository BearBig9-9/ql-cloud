#**ql-cloud**   ——Spring Cloud实践
    该项目作为鄙人学习spring cloud框架的实践项目，目前已经实现功能有：
    1. 数据库读写分离(基于mybatis)
    2. 服务注册(注册中心使用consul，需自行安装)
    3. 服务提供
    4. feign方式调用远程服务(鄙人比较倾向于对接口进行强约束,所以选择feign调用)
    5. zuul路由及过滤
    6. 服务监控
    7. maven将应用打包为tar压缩包
    欢迎交流，QQ:450457412
    项目git地址为: [https://git.oschina.net/qilong/ql-cloud](https://git.oschina.net/qilong/ql-cloud)
    配置文件项目git地址为: [https://git.oschina.net/qilong/ql-cloud-config](https://git.oschina.net/qilong/ql-cloud-config)
## 项目骨架及简介
* ql-cloud-commons
    项目公共部分
    * ql-cloud-commons-gateway
        Zuul路由及Zuul过滤实现
    * ql-cloud-common-base
        包括公共组件、公共model
    * ql-cloud-commons-utils
        各种工具类
* ql-cloud-framework
    项目核心系统
    * ql-cloud-framework-config
        项目核心配置
        * ql-cloud-framework-config
            项目公共配置文件
            * ql-cloud-framework-config-dao
                项目dao公共配置，实现mybatis读写分离，默认为一读一写
            * ql-cloud-framework-config-dao
                配置文件项目，读取云端git配置文件项目
    * ql-cloud-framework-sso
        计划作为单点登录项目，目前仅作为spring cloud调用demo
        * ql-cloud-framework-sso-dao
            dao层，放mybatis接口及mapper文件
        * ql-cloud-framework-entity
            实体包
        * ql-cloud-framework-iservice
            接口层，定义服务接口、实现断路由功能
        * ql-cloud-framework-service
            服务层，实现接口层定义的接口，对外提供服务
        * ql-cloud-framework-web
            应用层，web应用，调用远程服务