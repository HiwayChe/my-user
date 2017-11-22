<#global contextPath="${requestContext.contextPath}" />
<#macro page title="title" js=[] css=[]>
    <html><head><title>${title?html}</title><meta charset="UTF-8">
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <#list css as c><link rel="stylesheet" type="text/css" href="${c}"></#list></head>
    <body><@compress><#nested/></@compress>
    <script type="text/javascript" src="http://code.jquery.com/jquery-2.2.4.min.js"></script>
    <script type="text/javascript" src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <#list js as j><script type="text/javascript" src="${j}"></script></#list></body></html>
</#macro>