
<?xml version="1.0" encoding="utf-8"?>  <!-- -*- nxml -*- -->
<!DOCTYPE article [
<!ENTITY version "5.0">
<!--
<!ENTITY yes "<phrase role='unicode yes'>✔</phrase>">
<!ENTITY no "<phrase role='unicode no'>✘</phrase>">
-->
<!ENTITY yes "<phrase role='unicode yes'>YES</phrase>">
<!ENTITY no "<phrase role='unicode no'>NO</phrase>">
]>
<article xmlns="http://docbook.org/ns/docbook"
	 xmlns:xl="http://www.w3.org/1999/xlink"
	 version="5.0" xml:lang="en">
<bookinfo>
<title>${model.title}</title>
<subtitle>v. ${model.apiVersion}</subtitle>

<authorgroup>
	<#if model.contact??>
	<author><personname>${model.contact.name}</personname>
        <email>${model.contact.email!""}</email></author>
    </#if>
	<othercredit class="other" otherclass="contributor">
		<personname>Michael(tm) Smith</personname>
  		<email>smith@sideshowbarker.net</email>
  		<contrib>§dbxsl-ns</contrib>
	</othercredit>
</authorgroup>

<pubdate>${pubDate?datetime}</pubdate>

<#if model.termOfService??>
<legalnotice>
	<para>${model.termOfService}</para>
</legalnotice>
</#if>

</bookinfo>

<!--#include "./docbooktest.ftl"-->

<para>Automatically generated from: ${input} (in ${modelVersion} format)</para>

<#if model.license??>
<section xml:id="License">
	<title>License</title>
	<para>
		<#if model.license.url??>
			<ulink url="${model.license.url}"><citetitle>${model.license.name}</citetitle></ulink>
		<#else>
			${model.license}
		</#if>
	    <#if model.license.xImplementation??>
	    	<#assign xImpl=model.license.xImplementation>
	    	<#include "./ximplementation.ftl">
	    </#if>			
	</para>
</section>
</#if>

<#if model.termOfService??>
<section xml:id="ToS">
	<title>Terms of Service</title>
	<para><ulink url="${model.termOfService}"><citetitle>${model.termOfService}</citetitle></ulink></para>
</section>
</#if>


<#if model.externalDocs??>
<section xml:id="externalDocs">
	<title>External Documentation</title>
	<para>${md2docbook(model.externalDocs.description)} <ulink url="${model.externalDocs.url}"><citetitle>${model.externalDocs.url}</citetitle></ulink></para>
    <#if model.externalDocs.xImplementation??>
    	<#assign xImpl=model.externalDocs.xImplementation>
    	<#include "./ximplementation.ftl">
    </#if>		
</section>
</#if>

<#if model.tags??>
<#list model.tags>
<section xml:id="tags">
	<title>Tags</title>
	<para>The document contains the following tags:</para>
	<#items as tag> 
	<section xml:id="tags">
		<title>${tag.name}</title>
		${md2docbook(tag.description)} 
	    <#if tag.xImplementation??>
	    	<#assign xImpl=tag.xImplementation>
	    	<#include "./ximplementation.ftl">
	    </#if>		
		<#if tag.externalDocs??>
		<section xml:id="tagExternalDocs#${tag.name}">
			<title>External Documentation</title>
			<para>${md2docbook(tag.externalDocs.description)} <ulink url="${tag.externalDocs.url}"><citetitle>${tag.externalDocs.url}</citetitle></ulink></para>
		    <#if tag.externalDocs.xImplementation??>
		    	<#assign xImpl=tag.externalDocs.xImplementation>
		    	<#include "./ximplementation.ftl">
		    </#if>			
		</section>
		</#if>
	</section>
	</#items>
</section>
</#list>
</#if>

<#if model.security??>
<#list model.security>
<section xml:id="security">
	<title>Security</title>
	<#items as sec>
		<#list sec as key, list>
		<section xml:id="security${key}">
			<title>${key}</title>	
			<#list list as item>
			<para>${item}</para>
			</#list>
		</#list>
	    <#if sec.xImplementation??>
	    	<#assign xImpl=sec.xImplementation>
	    	<#include "./ximplementation.ftl">
	    </#if>		
		</section>		
	</#items>
</section>
</#list>
</#if>

<#if model.servers??>
<#list model.servers>
<section xml:id="servers">
	<title>Servers</title>
	<#items as server>
	<para>${md2docbook(server.description!"")} <ulink url="${server.url}"><citetitle>${server.url}</citetitle></ulink></para>
    <#if server.xImplementation??>
    	<#assign xImpl=server.xImplementation>
    	<#include "./ximplementation.ftl">
    </#if>	
	<#list server.variables>
	<section xml:id="securityVar">
		<title>Server Variables</title>
		<#items as key, var>
			<para>${key}</para>
			<para>${md2docbook(var.description)} - ${var.enum} - ${var.default}</para>
		</#items>
	</section>
	</#list>
	</#items>
</section>
</#list>
</#if>

<#if model.description??>
<section xml:id="introduction">
	<title>Introduction</title>	
	${md2docbook(model.description)}
    <#if model.xImplementation??>
    	<#assign xImpl=model.xImplementation>
    	<#include "./ximplementation.ftl">
    </#if>	
</section>
</#if>

<#if model.getPaths()??>
	<#list pathsById>
		<#items as api, paths>
<section xml:id="api${api}">
	<title>${api?cap_first!""} API</title>
	<#list paths as path>
	    <#if path.xImplementation??>
	    	<#assign xImpl=path.xImplementation>
	    	<#include "./ximplementation.ftl">
	    </#if>
		<#list path.operations as op>
	<section xml:id="operation${op.method!""}">
		<title>${op.method!""} ${path.path} ${op.operationId!""}<#if op.deprecated>(deprecated)</#if></title>
		<#if op.summary??>
		${md2docbook(op.summary)}
		</#if>
		<#if op.description??>
		${md2docbook(op.description)}
		</#if>
	    <#if op.xImplementation??>
	    	<#assign xImpl=op.xImplementation>
	    	<#include "./ximplementation.ftl">
	    </#if>
		<#if op.externalDocs??>
		<section xml:id="externalDocs${op.method!""}">
			<title>External Documentation</title>
			<para>${md2docbook(op.externalDocs.description)} <ulink url="${op.externalDocs.url}"><citetitle>${op.externalDocs.url}</citetitle></ulink></para>
		    <#if op.externalDocs.xImplementation??>
		    	<#assign xImpl=op.externalDocs.xImplementation>
		    	<#include "./ximplementation.ftl">
		    </#if>			
		</section>
		</#if>
		<#if op.pathParams??>
			<#assign paramTableTitle="Path Parameters">
			<#assign paramTableList=op.pathParams>
			<#include "./paramTable.ftl">
			<fo:block/>
		</#if>
		<#if op.headerAttributes??>
			<#assign paramTableTitle="Header Attributes">
			<#assign paramTableList=op.headerAttributes>
			<#include "./paramTable.ftl">
			<fo:block/>
		</#if>
		<#if op.queryParams??>		
			<#assign paramTableTitle="Query Parameters">
			<#assign paramTableList=op.queryParams>
			<#include "./paramTable.ftl">
			<fo:block/>
		</#if>
		<#if op.requestBody?? && op.requestBody.content??>
		<section xml:id="requestBody${op.method!""}">
			<title>Request Body <#if op.requestBody.required>(Required)</#if></title>
		    <#if op.requestBody.xImplementation??>
		    	<#assign xImpl=op.requestBody.xImplementation>
		    	<#include "./ximplementation.ftl">
		    </#if>			
			<#assign content=op.requestBody.content>
			<#include "./content.ftl">
		</section>
		<fo:block/>
		</#if>
		<#if op.responses??>
		<section xml:id="responses${op.method!""}">
			<title>Responses</title>
			<#list op.responses as key, response>
			<section xml:id="responses${op.method!""}">
				<title>${key?cap_first!""}</title>
				<#include "./response.ftl">			
			</section>
			</#list>
		</section>
		<fo:block/>
		</#if>
	</section>				
		</#list>	
	</#list>
</section>		
		</#items>
	</#list>
</#if>
<#if model.components??>
<section xml:id="components">
	<title>Components</title>
	<para>Components represent definitions available for reuse by the API implementation. They have no effects if not referenced by the real implementation.</para>
	<section xml:id="simpleTypes">
	<title>Simple Types</title>
	<para>Simple types are reusable fields having simple values such as: string, integer, boolean, arrays of simple objects, etc. In general one could say they do not refer to objects.</para>
	<#assign types=model.components.simpleTypes>
	<#assign typesTitle="Types List">
	<#include "./simpleTypesTable.ftl">
	</section>	
	<#list model.components.schemas>
	<section xml:id="schemas">
	<title>Schemas</title>
	<para>Schemas represent reusable objects or arrays of object.</para>
	<#items as key, schema>
		<section xml:id="schemas">
		<title>${key}</title>
		<#assign schemaTitle="Schema">
		<#include "./schemaTable.ftl">
		</section>	
	</#items>
	</section>
	</#list>
	<#list model.components.parameters>
	<section xml:id="parameters">
	<title>Parameters</title>
	<#items as key, parameter>
		<para>${key}=${parameter}</para>
		<fo:block/>
	</#items>
	</section>
	</#list>
	<#if model.components.examples??>
	<#assign item=model.components.examples>
	<#include "./examples.ftl">
	</#if>
	<#list model.components.requestBodies>
	<section xml:id="requestBodies">
	<title>Request Bodies</title>
	<#items as key, requestBody>
		<section xml:id="requestBodies${key}">
			<title>${key}</title>	
			<#assign content=requestBody.content>
			<#include "./content.ftl">
		</section>
	</#items>
	</section>
	</#list>
	<#list model.components.responses>
	<section xml:id="responses">
	<title>Responses</title>
	<#items as key, response>
		<section xml:id="responses}">
			<title>${key?cap_first!""}</title>
			<#include "./response.ftl">			
		</section>
	</#items>
	</section>
	</#list>
	<#list model.components.links>
	<section xml:id="links">
	<title>Links</title>
	<para>(has links)</para>	
	<#items as key, link></#items>
	</section>
	</#list>
</section>
</#if>