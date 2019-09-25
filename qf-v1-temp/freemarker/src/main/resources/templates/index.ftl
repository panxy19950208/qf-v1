<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    hi,${username}<br>
    id:${student.id}<br>
    name:${student.name}<br>
    age:${student.age}<br>
    date;${student.entryDate?date}
    date;${student.entryDate?time}
    date;${student.entryDate?datetime}
    message:${message!}
    message:${message!'123'}
    <#list list as stu>
        id:${stu.id}
        name:${stu.name}
        age:${stu.age}
        date:${stu.entryDate?datetime}
    </#list>
    <#if (target>1000)>
    小老弟
    <#elseif (target>10)>
    大佬
    <#else >
        没钱
    </#if>

</body>
</html>