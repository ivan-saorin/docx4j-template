<#list types>
		<table frame='all'><title>${typesTitle}</title>
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
	<#items as key, schema>
		<#list schema.properties>		
		<#items as key, item>
		<row>
		  <#if key == "+" || key == "or" || key == "and">
		  <entry namest="c1" nameend="c2" align="left"><type><emphasis>${key}</emphasis></type></entry>
		  <#else> 
		  <entry><type><#if (item.dataType?? && item.dataType.required?? && item.dataType.required) || (item.required?? && item.required)><emphasis role="strong">${key!""}</emphasis><#else>${key!""}</#if></type></entry>
		  <entry><type>${xmlEscaping(item)}</type></entry>
		  <entry>${md2docbook(item.description!"")}
		  	<#if item.xImplementation??>
		    	<#assign xImpl=item.xImplementation>
		    	<#include "./ximplementation.ftl">
		    </#if>
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
		</#list>
	</#items>
		</tbody>
		</table>			
</#list>