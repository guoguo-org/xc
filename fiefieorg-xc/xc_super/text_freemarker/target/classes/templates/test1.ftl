<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Hello World!</title>
</head>
<body>
Hello ${name}!

<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>钱包</td>
        <td>日期</td>

    </tr>
    <#if stus??>
    <#list stus as stu>
        <tr>
            <td>${stu_index + 1}</td>
            <td>${stu.name}</td>
            <td>${stu.age}</td>
            <td>${stu.mondy}</td>
            <td>${stu.birthday?date}</td>
        </tr>
    </#list>
    </#if>
</table>


输出stu1的学生信息：
<br/>
姓名：${stuMap['stu1'].name}
<br/>
年龄：${stuMap['stu1'].age}
<br/>
3、输出 1.3.1.4 if指令 if 指令即判断指令，
是常用的FTL指令，freemarker在解析时遇到if会进行判断，
条件为真则输出if中间的内容，否 则跳过内容不再输出。
1、数据模型： 使用list指令中测试数据模型。
2、模板： 输出stu1的学生信息：
<br/>
姓名：${stuMap.stu1.name}
<br/>
年龄：${stuMap.stu1.age}
<br/>
遍历输出两个学生信息：
<br/>
<table>
    <tr>
        <td>序号</td>
        <td>姓名</td>
        <td>年龄</td>
        <td>钱包</td>
    </tr>
    <#list stuMap ? keys as k>
        <tr>
            <td>${k_index + 1}</td>
            <td>${stuMap[k].name}</td>
            <td>${stuMap[k].age}</td>
            <td>${stuMap[k].mondy}</td>
        </tr>
    </#list>
</table>


<table>
    <tr>
        <td>姓名</td>
        <td>年龄</td>
        <td>钱包</td>
    </tr>
    <#--通过阅读上边的代码，实现的功能是：如果姓名为“小明”则背景色显示为红色。
    3、输出： 通过测试发现 姓名为小明的背景色为红色。
    1.3.2 其它指令 1.3.2.1 运算符 1、算数运算符 FreeMarker表达式中完全支持算术运算,
    FreeMarker支持的算术运算符包括:+, - , * , / , % 2、逻辑 运算符 逻辑运算符有如下几个:
    逻辑与:&& 逻辑或:|| 逻辑非:! 逻辑运算符只能作用于布尔值,否则将产生错误
    3、 比较运算符 表达式中支持的比较运算符有如下几个: 1 =或者==:判断两个值是否相等.
    2 !=:判断两个值是否不等.
    3 > 或者gt:判断左边值是否大于右边值 4 >=或者gte:判断左边值是否大于等于右边值 5 <或者lt:判断左边值是否小于右
            边值 6 <=或者lte:判断左边值是否小于等于右边值 注意: =和!=可以用于字符串,数值和日期来比较是否相等,但=和!=两边必须是相同类型的值,否则会产生错误,而且 FreeMarker是精确比较,"x","x ","X"是不等的.其它的运行符可以作用于数字和日期,但不能作用于字符串,大部分的时 候,使用gt等字母运算符代替>会有更好的效果,因为 FreeMarker会把>解释成FTL标签的结束字符,当然,也可以使用括 号来避免这种情况,如:<#if (x>y)> 1.3.2.2 空值处理 1、判断某变量是否存在使用 “??” 用法为:variable??,如果该变量存在,返回true,否则返回false 例：为防止stus为空报错可以加上判断如下： 2、缺失变量默认值使用 “!” 使用!要以指定一个默认值，当变量为空时显示默认值。 例： ${name!''}表示如果name为空显示空字符串。 如果是嵌套对象则建议使用（）括起来。
    -->
    <#if stus??>
    <#list stus as stu>
        <tr>
            <td <#if stu.name =='小明'> style="background:red;" </#if>> ${stu.name}</td>
            <td>${stu.age}</td>
            <td <#if stu.mondy gt 300 >style="background: red;" </#if>>${stu.mondy}</td>
        </tr>
    </#list>
    </#if>
</table>

${(stus.name)!''}

</body>
</html>