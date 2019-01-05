<#if item.example??>
	<example>
	<title>Example2</title>
	<programlisting>${item.example}
    <#if item.xImplementation??>
    	<#assign xImpl=item.xImplementation>
    	<#include "./ximplementation.ftl">
    </#if>	
	</programlisting>
	</example>
</#if>
<#include "./examples.ftl">
