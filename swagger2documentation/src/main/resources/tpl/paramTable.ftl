<#list paramTableList>
<table frame='all'><title>${paramTableTitle}</title>
<colspec colname='c1'/>
<colspec colname='c2'/>
<colspec colname='c3'/>
<thead>
<row>
  <entry><emphasis role="strong">Field name</emphasis></entry>
  <entry><emphasis role="strong">Data type</emphasis></entry>
  <entry><emphasis role="strong">Description</emphasis></entry>
</row>
</thead>
<tbody>
<#items as item>
<row>
  <entry><type>${item.name!""}</type>
  <variablelist>
	  <#if (item.dataType?? && item.dataType.required?? && item.dataType.required) || (item.required?? && item.required)><varlistentry><term><type>(required)</type></term></varlistentry></#if>
	  <#if item.deprecated?? && item.deprecated><varlistentry><term><type>(deprecated)</type></term></varlistentry></#if>
	  <#if item.readOnly?? && item.readOnly><varlistentry><term><type>(readOnly)</type></term></varlistentry></#if>
	  <#if item.allowEmptyValues?? && item.allowEmptyValues><varlistentry><term><type>(allowEmptyValues)</type></term></varlistentry></#if>
	  <#if item.allowReserved?? && item.allowReserved><varlistentry><term><type>(allowReserved)</type></term></varlistentry></#if>
  </variablelist>
  </entry>
  <entry><type>${xmlEscaping(item.dataType!"")}</type></entry>
  <entry>${md2docbook(item.description!"")}	
	<#if item.defaultValue??>default: ${item.defaultValue}</#if>
	<#if item.dataType??>		
		<#if item.dataType.enumValues??>
			<#list item.dataType.enumValues>
				<para>Possible Values:</para>
				<itemizedlist mark='bullet'>
				<#items as enum>
					<listitem><para>${enum}</para></listitem>						
				</#items>			
				</itemizedlist>
			</#list>
		</#if>
		<#assign item=item.dataType>
		<#include "./all_examples.ftl">
	</#if>
    <#if item.xImplementation??>
    	<#assign xImpl=item.xImplementation>
    	<#include "./ximplementation.ftl">
    </#if>
	<#if item.content??>content: ${item.content}</#if>
	<#include "./all_examples.ftl">
  </entry>
</row>
</#items>
</tbody>
</table>
</#list>