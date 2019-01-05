<#if item.examples??>
	<#list item.examples>
	<section xml:id="Examples">
		<title>Examples</title>
		<para>	
		<#items as key, example>
	<example><title>${key}</title>
	<#if example?is_string>
		${md2docbook(example)}
	<#else>
		<#if example.summary??>
		<para>${md2docbook(example.summary)}</para>
		</#if>
		<#if example.description??>
		<para>${md2docbook(example.description)}</para>
		</#if>
		<#if example.value??>
		<programlisting>${example.value}</programlisting>
		</#if>
	</#if>
	</example>
		</#items>
		</para>
	</section>		
	</#list>
</#if>
