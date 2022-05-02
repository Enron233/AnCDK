# AnCDK
 MineCraft服务器CDKey插件，使用这个插件，你可以创建绑定命令的CDK，当玩家输入CDK时，服务器便可以执行相应的指令，本插件可作为连通服务器内与现实世界的桥梁，广泛应用于：“服务器赞助系统、MineCraft无人售卡、服务器活动奖品发放”等多个方面。

## 插件特性

1. 全自动一键生成9位以上CDK
2. 可批量生成同类型CDK
3. 可设置指令的执行方式（控制台/玩家）
4. 插件轻量化
5. 配置文件高度自定义
6. 可自定义添加CDK
7. 支持批量导出CDK
8. （赶紧点击回复，马上你的建议将成为插件的特♂色~

## 插件命令

> 本插件命令还有如下写法：`ancdkey`, `cdk`, `cdkey`

> 管理员命令:

| 命令 | 功能 |
| ---- | ---- |
| /ancdk create [command] [num] | 创建[num]个执行[command]命令的CDK |
| /ancdk export | 批量一键导出所有CDK |
| /ancdk reload | 重载插件配置文件 |

> 玩家命令:

| 命令 | 功能 |
| ---- | ---- |
| /ancdk run [CDK] | 使用CDK。`run`也可换为`get` |

## 插件权限

> 管理员权限

| 节点 | 描述 |
| ---- | ---- |
| `ancdk.admin` | 总权限。给予此权限后无需剩余管理员权限 |
| `ancdk.admin.create` | 创建CDK |
| `ancdk.admin.export` | 导出CDK |
| `ancdk.admin.reload` | 重载 |

> 用户权限

| 节点 | 描述 |
| ---- | ---- |
| `ancdk.user` | 使用CDK的权限 |

## 插件变量：
 
> `{player}`                         代表使用CDK的玩家


## 配置文件

```yaml
Bukkit/Spigot配置文件示例： 
1ll73hur1bhm:                           ## CDKey内容
   command: 'eco give {player} 100 '    ## 使用CDK后要执行的指令
   op: true                             ### 是否以OP（控制台）身份执行指令
   only: true                           ### 是否只能执行一次
```

```hocon
Sponge配置文件示例： 
"1ll73hur1bhm" {                        ## CDKey内容
    command="eco give {player} 100"     ## 使用CDK后要执行的指令
    console=true                        ### 是否以控制台身份执行指令
    once=true                           ### 是否只能执行一次
}
```

## 使用教程&图文介绍

例1：生成10个CDK，玩家执行后获得100枚游戏币

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

***

例2：手动添加1个永久的CDK，玩家执行后获得5个钻石

思路：打开配置文件并手动写入CDK，绑定指令为`/give {player} minecraft:diamond 5`

1. 使用任意文本编辑器打开`config/ancdk/ancdk.conf`文件

2. 按照配置文件示例格式进行书写。这里我们将`5diamond`作为CDK的内容。注意once设置为false（目前还未实现每个玩家限领取一次。后续会完善）

![example2](https://github.com/Lileep/AnCDK/blob/sponge/sample_img/example2.png)

3. 在服务端/客户端中输入`/ancdk reload`重载插件，即可在游戏中输入`/ancdk run 5diamond`领取该CDK


## 插件下载

Bukkit/Spigot: https://enron.lanzouv.com/b00pee79a 密码:giih

Sponge: https://wwt.lanzouy.com/iZVVV045zkej 密码:1ddq

或在仓库release中下载

## 开源地址

[https://github.com/Enron233/AnCDK](https://github.com/Enron233/AnCDK "https://github.com/Enron233/AnCDK")

### 我是小安~ 感谢你的使用~~
### Sponge版AnCDK，由触手百合（Lileep）开发
