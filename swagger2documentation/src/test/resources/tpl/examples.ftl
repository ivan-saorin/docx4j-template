<#if item.examples??>
	<#list item.examples>
	<section xml:id="Examples">
		<title>Examples</title>
		<para>	
		<#items as key, example>
	<para><emphasis>${key}</emphasis>
	<#if example?is_string>
		<programlisting language="javascript">${json(example)}</programlisting>
	<#else>
		<#if example.summary??>
		<para>${md2docbook(example.summary)}</para>
		</#if>
		<#if example.description??>
		<para>${md2docbook(example.description)}</para>
		</#if>
		<#if example.value??>
		<programlisting language="javascript">${json(example.value)}</programlisting>
		</#if>
	    <#if item.xImplementation??>
	    	<#assign xImpl=example.xImplementation>
	    	<#include "./ximplementation.ftl">
	    </#if>			
	</#if>
	</para>
		</#items>
		</para>
	</section>		
	</#list>
</#if>
