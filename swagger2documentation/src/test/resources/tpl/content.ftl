<#list content as key, item>
	<para>${key}</para>
	<#include "./all_examples.ftl">	
	<#if item.schema??>
		<example><title>Schema</title></example>
		<programlisting>${item.schema}</programlisting>
    </#if>
</#list>