<#list schema>
<table frame='all'><title>${schemaTitle}</title>
<colspec colname='c1'/>
<colspec colname='c2'/>
<colspec colname='c3'/>
<thead>
<row>
  <entry><emphasis role="strong">Field name / Ref</emphasis></entry>
  <entry><emphasis role="strong">Data type</emphasis></entry>
  <entry><emphasis role="strong">Description</emphasis></entry>
</row>
</thead>
<tbody>
<#items as key, item>
<row>
  <#if key == "+" || key == "or" || key == "and">
  <entry namest="c1" nameend="c2" align="left"><emphasis>${key}</emphasis></entry>
  <#else> 
  <entry><#if (item.dataType?? && item.dataType.required?? && item.dataType.required) || (item.required?? && item.required)><emphasis role="strong">${key!""}</emphasis><#else>${key!""}</#if></entry>
  <entry>${xmlEscaping(item)}</entry>
  <entry>${md2docbook(item.description!"")}
	<#if item.dataType??>
		${item.dataType.required?c}	
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
	<#include "./all_examples.ftl">
  </entry>
  </#if>
</row>
</#items>
</tbody>
</table>
</#list>