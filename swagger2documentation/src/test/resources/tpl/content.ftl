<#list content as key, item>
	<para>${key}</para>
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
	<#if item.schema??>
		<example><title>Schema</title></example>
		<programlisting>${item.schema}</programlisting>
    </#if>
</#list>