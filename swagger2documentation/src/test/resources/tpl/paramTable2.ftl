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
<#items as key, item>
<row>
  <entry>${key!""}
  <#if item.deprecated??>(deprecated)</#if>
  <#if item.readOnly??>(readOnly)</#if>
  <#if item.allowEmptyValues??>(allowEmptyValues)</#if>
  <#if item.allowReserved??>(allowReserved)</#if>
  </entry>
  <entry>${item.dataType!""}</entry>
  <entry>${item.description!""}

	<#if item.defaultValue??>default: ${item.defaultValue}</#if>
	<#if item.enumValue??>enum: ${item.enumValue}</#if>
	<#if item.content??>content: ${item.content}</#if>
	<#if item.example??>
		<example><title>Example</title></example>
		<programlisting>${item.example}</programlisting>
    </#if>
	<#if item.examples??>
		<#list item.examples>
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
  </entry>
</row>
</#items>
</tbody>
</table>
</#list>