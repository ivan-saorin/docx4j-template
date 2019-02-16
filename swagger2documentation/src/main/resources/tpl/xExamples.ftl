<#list item.xExamples>
	<section xml:id="Examples">
	<title>Examples</title>
	<para>	
	<#items as case>	
		<#list case>
			<#assign title=0>
			<#assign description=0>
			<#assign note=0>
			<#assign example=0>
		
			<#items as fn, v>
				<#if fn=="title">
					<#assign title=v>
				</#if>			
				<#if fn=="description">
					<#assign description=v>
				</#if>
				<#if fn=="note">
					<#assign note=v>
				</#if>
				<#if fn=="example">
					<#assign example=v>
				</#if>
			</#items>
		</#list>
		<section xml:id="ExampleCase">
		<#if title?? && title?is_string>
			<title>${md2docbook(title)}</title>
		</#if>
		<#if description?? && description?is_string>
			<para>${md2docbook(description)}</para>
		</#if>
		<#if example?? && example?is_string>
			<programlisting language="javascript">${example}</programlisting>
		</#if>
		</section>		
	</#items>
	</para>
	</section>
</#list>
