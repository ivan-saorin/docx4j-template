<#if response.description??>
	<para>${md2docbook(response.description)}</para>
</#if>
<#if response.xImplementation??>
	<#assign xImpl=response.xImplementation>
	<#include "./ximplementation.ftl">
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
<#if response.schema??>
	<#assign schemaTitle="Schema">
	<#assign schema=response.schema>
	<#include "./schemaTable.ftl">
</#if>
<#if response.content??>
	<#assign content=response.content>
	<#include "./content.ftl">
</#if>
<#assign item=response>
<#include "./all_examples.ftl">			
