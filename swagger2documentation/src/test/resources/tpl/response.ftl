<#if response.description??>
	<para>${response.description}</para>
</#if>
<#if response.produces??>
	<#list response.produces>
		<#items as produce>
			<para>${produce}</para>
		</#items>
	<#else>
		<para>*/*</para>
	</#list>
<#else>
	<para>*/*</para>
</#if>	
<#if response.ref??>
	<para>Reference Model: ${response.ref}</para>
</#if>
<#if response.headerAttributes??>
	<#assign paramTableTitle="Header Attributes">
	<#assign paramTableList=response.headerAttributes>
	<#include "./paramTable.ftl">
	<fo:block/>
</#if>
<#list response.links>
	<para>(has links)</para>
	<#items as link></#items>
</#list>
<#if response.examples??>
	<#list response.examples>
		<#items as key, example>
	<example><title>${key}</title></example>
			<#if example.summary??>
	<para>${example.summary}</para>
			</#if>
			<#if example.description??>
	<para>${example.description}</para>
			</#if>
			<#if example.value??>
	<programlisting>${example.value}</programlisting>
			</#if>
		</#items>
	</#list>	
</#if>
<#if response.schema??>
	<example><title>Schema</title></example>
	<programlisting>${response.schema}</programlisting>
</#if>
<#if response.content??>
	<#assign content=response.content>
	<#include "./content.ftl">
</#if>
