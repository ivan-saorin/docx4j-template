<#if item.example??>
	<para>
	<emphasis>Example</emphasis>
	<programlisting language="javascript">${json(item.example)}</programlisting>
	<programlisting>
    <#if item.xImplementation??>
    	<#assign xImpl=item.xImplementation>
    	<#include "./ximplementation.ftl">
    </#if>	
	</programlisting>
	</para>
</#if>
<#include "./examples.ftl">
