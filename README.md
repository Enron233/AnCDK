# AnCDK
 MineCraft服务器CDKey插件，使用这个插件，你可以创建绑定命令的CDK，当玩家输入CDK时，服务器便可以执行相应的命令，本插件可作为连通服务器内与现实世界的桥梁，广泛应用于：“服务器赞助系统、MineCraft无人售卡、服务器活动奖品发放”等多个方面。

## 插件特性

1. 全自动一键生成9位以上CDK
2. 可批量生成同类型CDK
3. 可设置命令的执行方式（控制台/玩家）
4. 插件轻量化
5. 配置文件高度自定义
6. 可自定义添加CDK
7. 支持批量导出CDK
8. （赶紧点击回复，马上你的建议将成为插件的特♂色~

## 插件命令

### Bukkit/Spigot:

> 管理员命令:

| 命令 | 功能 |
| ---- | ---- |
| /ancdk create [命令] [数量] | 创建`[数量]`个执行`[命令]`的CDK |
| /ancdk export | 批量一键导出所有CDK |
| /ancdk reload | 重载插件配置文件 |

> 玩家命令:

| 命令 | 功能 |
| ---- | ---- |
| /ancdk [CDK] | 使用CDK |

### Sponge:

> 本插件命令还有如下写法：`ancdkey`, `cdk`, `cdkey`

> 管理员命令:

| 命令 | 功能 |
| ---- | ---- |
| /ancdk create <--once> [数量] [命令] | 创建`[数量]`个执行`[命令]`的CDK <br> 命令前可使用`console:`指定控制台执行 <br> 使用`--once`参数可让CDK为一次性，否则每个玩家均可使用一次此CDK <br> 一次执行多个命令可以使用`;`分隔 |
| /ancdk export <csv> | 批量一键导出所有CDK。使用`csv`参数可以仅将所有cdkey导出为csv文件 |
| /ancdk reload | 重载插件配置文件 |

> 玩家命令:

| 命令 | 功能 |
| ---- | ---- |
| /ancdk [CDK] | 使用CDK |

## 插件权限

> 管理员权限

| 节点 | 描述 |
| ---- | ---- |
| `ancdk.admin` | 总权限。给予此权限后无需剩余管理员权限 |
| `ancdk.admin.create` | 创建CDK（仅sponge） |
| `ancdk.admin.export` | 导出CDK（仅sponge） |
| `ancdk.admin.reload` | 重载（仅sponge） |

> 用户权限

| 节点 | 描述 |
| ---- | ---- |
| `ancdk.user` | 使用CDK的权限（仅sponge，Bukkit/Spigot无需此权限即可使用） |

## 插件变量：
 
> `{player}`                         代表使用CDK的玩家


## 配置文件

```yaml
Bukkit/Spigot配置文件示例： 
1ll73hur1bhm:                           ## CDKey内容
   command: 'eco give {player} 100 '    ## 使用CDK后要执行的命令
   op: true                             ### 是否以OP（控制台）身份执行命令
   only: true                           ### 是否只能执行一次
```

```hocon
Sponge配置文件示例（cdks.conf）： 
仅可执行一次的CDK： 
"1ll73hur1bhm" {                                 ## CDKey内容
    command="eco give {player} 100"      ## 使用CDK后要执行的命令。可在命令前添加"console:"来指定是否以控制台身份执行
}

每个玩家均可执行一次的CDK： 
"4v1j6bjvtti" {                                             ## CDKey内容
    command="console:give {player} minecraft:diamond 5"     ## 使用CDK后要执行的命令
    usedPlayer=[                                            ### 玩家列表。执行过的玩家会被记录在此，默认为空（即[]）
        Lileep
    ]
}
```

## 翻译（目前仅sponge）

使用压缩软件找到插件jar包内的`assets/ancdk/lang`文件夹，或者下载发行版中的`lang.zip`文件，随后解压你要使用的语言文件

将语言文件重命名为`language.conf`并替换掉配置目录（默认`config/ancdk`）下的同名文件

重启服务器/重载插件即可

目前本插件自带的翻译文件有：

* English

* 中文（简体）

* 中文（繁體）

## 使用教程&图文介绍

例1（Bukkit/Spigot）：生成10个CDK，玩家执行后获得100枚游戏币

思路：生成10个CDK，绑定命令为"/eco give {player} 100"

1. 使用命令“/ancdk create eco give {player} 100 10”批量生成CDK！

[![](https://s2.loli.net/2022/02/20/7V8fLOdnK2EztXg.png)](https://s2.loli.net/2022/02/20/7V8fLOdnK2EztXg.png)

2. Bukkit/Spigot端到插件配置文件`“plugins/AnCDK”`目录下，打开Config.yml，获取CDK！

Sponge端为`config/ancdk`目录下的`cdks.conf`

[![](https://s2.loli.net/2022/02/20/8z91nwyL6lGihPF.png)](https://s2.loli.net/2022/02/20/8z91nwyL6lGihPF.png)

3. 复制CDK发送给亲爱的玩家~~

[![](https://s2.loli.net/2022/02/20/EuneWjSbaCNtsrV.png)](https://s2.loli.net/2022/02/20/EuneWjSbaCNtsrV.png)

4. 亲爱的玩家到服务器使用命令“/ancdk [CDK]”使用CDK

[![](https://s2.loli.net/2022/02/20/L1INUvGDcYpRSPy.png)](https://s2.loli.net/2022/02/20/L1INUvGDcYpRSPy.png)

5. CDK使用成功！玩家很开心！

[![](https://s2.loli.net/2022/02/20/z2hRmLHdtKkB83x.png)](https://s2.loli.net/2022/02/20/z2hRmLHdtKkB83x.png)

***

例2（Sponge）：手动添加1个永久的CDK，每个玩家执行后控制台会为其发放5个钻石

思路：打开配置文件并手动写入CDK，绑定命令为`console:give {player} minecraft:diamond 5`

1. 使用任意文本编辑器打开`config/ancdk/cdks.conf`文件

2. 按照配置文件示例格式进行书写。这里我们将`5diamond`作为CDK的内容，并将指令和空的已使用玩家列表写进去

![example2](https://github.com/Lileep/AnCDK/blob/sponge/sample_img/example2.png)

3. 输入`/ancdk reload`重载插件或直接重启，即可在游戏中输入`/ancdk 5diamond`领取该CDK


## 插件下载

Bukkit/Spigot: https://enron.lanzouv.com/b00pee79a 密码:giih

Sponge: https://wwt.lanzouy.com/ipDUH04cg5ad 密码:d3zi

或在仓库release中下载

## 开源地址

Bukkit/Spigot:

[https://github.com/Enron233/AnCDK](https://github.com/Enron233/AnCDK "https://github.com/Enron233/AnCDK")

Sponge:

[https://github.com/Lileep/AnCDK/tree/sponge](https://github.com/Lileep/AnCDK/tree/sponge "https://github.com/Lileep/AnCDK/tree/sponge")

### 我是小安~ 感谢你的使用~~
### Sponge版AnCDK，由触手百合（Lileep）开发
