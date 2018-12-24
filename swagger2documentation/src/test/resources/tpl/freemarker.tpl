<html>
<head>
    <title>FreeMarker</title>
    <meta http-equiv="Content-Type" content="text/html; charset=${target}"/>
    <style type="text/css">
        body { font-size: 10pt; color: #333333; background-color: AliceBlue }
        thead { font-weight: bold; background-color: #C8FBAF; }
        table { width: 98%; left: 20px; font-decoration: none }
        td { font-size: 10pt; text-align: left; margin: 1px solid black }
        .left { text-align: left; }
        .center { text-align: center; }
        .right { text-align: right; }
        .bold { font-weight: bold }
        .italic { font-decoration: italic }
        .normal { font-weight: normal; font-decoration: none }        
        .odd { background-color: #F3DEFB; color: black }
        .even { background-color: #EFFFF8; }
    </style>
</head>
<body>
    <h1>${model.getTitle()}</h1>
    <h2>v.${model.getApiVersion()} - Open Api v.${model.getOpenApiVersion()}</h2>
    <br>
    <p><i>${model.getContact().getName()} (${model.getContact().getEmail()})</i> - ${model.getContact().getUrl()}</p>
    <br>
    <p><i>${model.getLicense().getName()}</i> - ${model.getLicense().getUrl()}</p>
    <br>
    <#if (model.getTermOfService())>
    <h3>Term Of Service</h3>
    <p>${model.getTermOfService()}</p>
    <br>
    </#if>
    <p>${model.getDescription()}</p>
    <br>
	<#list model.getPaths() as path>
		<h2>${path.getPath()}</h2>
		<br>
		<#list path.getOperations() as operation>
			<h3>${operation.getMethod()} <i>${path.getPath()}</i></h3>
			<p>${operation.getSummary()}</p>			
			<#assign list = operation.getPathParams()>
			<#if (list)>
				<br>
				<h4>Path Parameters:</h4>
				<#include "./paramTable.tpl">				
			</#if>
			<#assign list = operation.getHeaderAttributes()>
			<#if (list)>
				<br>
				<h4>Header Attributes:</h4>
				<#include "./paramTable.tpl">				
			</#if>					
			<#assign list = operation.getQueryParams()>
			<#if (list)>
				<br>
				<h4>Query Parameters:</h4>
				<#include "./paramTable.tpl">				
			</#if>					
		
		</#list>		
	</#list>
    
    <!--
    <table>
        <thead>
            <tr>
                <th width="40px">序号</th>
                <th width="40px">编码</th>
                <th width="120px">名称</th>
                <th width="120px">日期</th>
                <th width="40px">布尔</th>
                <th width="80px">值</th>
            </tr>
        </thead>
        <tbody>
            <#list models as model>
            <tr class="${["odd", "even"][model_index % 2]}">
                <td>${model_index}</td>
                <td>${model.code}</td>
                <td>${model.name}</td>
                <#if (format)>
                <td>${model.date?string("yyyy-MM-dd HH:mm:ss")}</td>
                <#else>
                <td>${model.date?datetime}</td>
                </#if>
                <td>${model.bool?string("true", "false")}</td>
                <#if (model.value > 105.5)>
                <td style="color: red;">${model.value}</td>
                <#else>
                <td style="color: blue;">${model.value}</td>
                </#if>
            </tr>
            </#list>
        </tbody>
    </table>
    -->
</body>
</html>