# AnCDK
 MineCraft服务器CDKey插件，使用这个插件，你可以创建绑定命令的CDK，当玩家输入CDK时，服务器便可以执行相应的指令，本插件可作为连通服务器内与现实世界的桥梁，广泛应用于：“服务器赞助系统、MineCraft无人售卡、服务器活动奖品发放”等多个方面。

## 插件特性

1. 全自动一键生成9位以上CDK
2. 可批量生成同类型CDK
3. 可设置指令的执行方式（控制台/玩家）
4. 插件轻量化
5. 配置文件高度自定义
6. 可自定义添加CDK
7. 支持批量导出CDK（开发中...）
8. （赶紧点击回复，马上你的建议将成为插件的特♂色~

## 插件命令

> 管理员命令(需要“ancdk.admin"权限):
>
> /ancdk create [command] [num]    创建[num]个执行[command]命令的CDK
> /ancdk reload                    重载插件配置文件
>
> 玩家命令:
>
> /ancdk [CDK]                     使用CDK
>
> 插件变量：
> {player}                         代表使用CDK的玩家

## 配置文件
```yaml
配置文件示例： 
1ll73hur1bhm:                           ## CDKey内容
   command: 'eco give {player} 100 '    ## 使用CDK后要执行的指令
   op: true                             ### 是否以OP（控制台）身份执行指令
```

## 使用教程&图文介绍

例：生成10个CDK，玩家执行后获得100枚游戏币
思路：生成10个CDK，绑定指令为"/eco give {player} 100"
1. 使用指令“/ancdk create eco give {player} 100 10”批量生成CDK！

[![](https://s2.loli.net/2022/02/20/7V8fLOdnK2EztXg.png)](https://s2.loli.net/2022/02/20/7V8fLOdnK2EztXg.png)

2. 到插件配置文件“plugins/AnCDK”目录下，打开Config.yml，获取CDK！

[![](https://s2.loli.net/2022/02/20/8z91nwyL6lGihPF.png)](https://s2.loli.net/2022/02/20/8z91nwyL6lGihPF.png)

3. 复制CDK发送给亲爱的玩家~~

[![](https://s2.loli.net/2022/02/20/EuneWjSbaCNtsrV.png)](https://s2.loli.net/2022/02/20/EuneWjSbaCNtsrV.png)

4. 亲爱的玩家到服务器使用指令“/ancdk [CDK]”使用CDK

[![](https://s2.loli.net/2022/02/20/L1INUvGDcYpRSPy.png)](https://s2.loli.net/2022/02/20/L1INUvGDcYpRSPy.png)

5. CDK使用成功！玩家很开心！

[![](https://s2.loli.net/2022/02/20/z2hRmLHdtKkB83x.png)](https://s2.loli.net/2022/02/20/z2hRmLHdtKkB83x.png)

## 插件下载
[https://enron.lanzouv.com/ix93s00cy9of](https://enron.lanzouv.com/ix93s00cy9of "https://enron.lanzouv.com/ix93s00cy9of")
## 开源地址

[https://github.com/Enron233/AnCDK](https://github.com/Enron233/AnCDK "https://github.com/Enron233/AnCDK")

### 我是小安~ 感谢你的使用~~
