<#list item.xExamples>
<section xml:id="Examples">
	<title>Examples</title>
	<para>	
	<#items as key, case>
	<section xml:id="Examples">
		<title>${key}</title>
		<para>
		<#list case>
		<section xml:id="Case">
			<#items as mediaType, example>
	<title>${mediaType}</title>
	<para>	
		<#if example.summary??>
		<para>${md2docbook(example.summary)}</para>
		</#if>
		<#if example.description??>
		<para>${md2docbook(example.description)}</para>
		</#if>
		<#if example.note??>
		<note><title>Changes:</title>
		<para>${md2docbook(example.note)}</para>
		</note>		
		</#if>		
		<#if example.value??>
		<programlisting language="javascript">${json(example.value)}</programlisting>
		</#if>
	</para>
			</#items>
	</section>		
		</#list>

	</para>
	</section>
	</#items>
	</para>
	</section>		
</#list>
