
# KUTILS 
 此工具包的目的为致力于减少开发工作中的重复性工作，比如一些格式化和校验类的代码，当然随着JDK的更新一些工具化的操作已经有一部分集成进JDK中，已经非常便利，此工具包希望作为一种补充，为开发人员减少些许劳动量。

## TemplateBuilder
基于Freemarker 的代码生成器
## Dates
 日期处理工具
## Excels
 该工具主要是在导出数据事使用，一般来说 excel 类型文件的导出是比较常见的功能，而且格式也是很固定的，比较适合做成工具使用。
这里主要有两种数据导出格式，一种是数据量较少时，使用通用常见的 `xlsx` 类型的文件导出，但是如果导出的文件数据量较大，使用xlsx类型文件的话可能会消耗过多的内存，这种情况下我们可以选择 xml 类型的文件的导出。
xlsx 文件底层实现是使用的xml实现的，因此我们导出的xml格式的文件可以直接使用Office Excel 软件直接打开操作即可，我们在拼接xml 字符串时不会像使用POI框架一样产生大量的对象，减少了内存的支出因此在同样的内存下可以导出更多的数据。
## Files
 文件处理工具
## Numerics
数值处理工具
## Strings
字符串处理工具
## Currencys
资金处理工具