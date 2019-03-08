## RRD-api2doc
RRD-api2doc基于api2doc进行修改，支持解析FastJson数据结构作为输入参数。

## Features
### @Api2Doc 注解详述

Api2Doc 一共有 3 个注解：@Api2Doc、@ApiComment 及 @ApiError 。

@Api2Doc 用于对文档的生成进行控制。

@Api2Doc 修饰在类上，表示这个类会参与到文档生成过程中，Api2Doc 服务 会扫描 Spring 容器中所有的 Controller 类，只有类上有 @Api2Doc 的类， 才会被生成文档，一个类对应于文档页面左侧的一级菜单项，@Api2Doc 的 name 属性则表示这个菜单项的名称。

@Api2Doc 也可以修饰在方法，不过在方法上的 @Api2Doc 通常是可以省略， Api2Doc 服务会扫描这个类的所有带有 @RequestMapping 的方法， 每个这样的方法对应文档页面的左侧的二级菜单项， 菜单项的名称取 @RequestMapping 的 name 属性，当然您仍然可以在方法上用 @Api2Doc 的 name 属性进行重定义。

### @ApiComment 注解详述
@ApiComment 用于对 API 进行说明，它可以修饰在很多地方：

修饰在类上，表示对这组 API 接口进行说明；
修饰在方法上，表示对这个 API 接口进行说明；
修饰在参数上，表示对这个 API 接口的请求参数进行说明；
修饰在返回类型的属性上，表示对这个 API 接口的返回字段进行说明；
修饰在枚举项上，表示对枚举项进行说明；

### @ApiError 注解详述
@ApiError 用于定义错误码，有的 API 方法在执行业务逻辑时会产生错误， 出错后会在返回报文包含错误码，以方便客户端根据错误码作进一步的处理， 因此也需要在 API 文档上体现错误码的说明。

### @ApiParamComments
@ApiParamComments可以声明多个参数描述，注意使用此声明，将不会获取方法参数作为文档参数描述。

## Get Started
1. 入口类中加入@EnableApi2Doc
2. Controller类上加入@Api2Doc
3. @RequestMapping中name自动为文档索引描述
4. domain中成员变量可以通过@ApiComment声明描述
5. Controller方法上支持使用@ApiParamComments声明请求参数列表
6. 支持@ApiError声明异常

## License
Copyright (c) Renrendai Business Corporation. All rights reserved.

Licensed under the [MIT License](./LICENSE).