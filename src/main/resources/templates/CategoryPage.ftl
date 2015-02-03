${xml}
=== UML ===
${uml}
<#list linkedSchemas as linkedSchema>
* see also [[:Category:${linkedSchema.category}]]
</#list>    
=== Documentation ===
${documentation}
=== Concept ===
{{Concept
|name=${pageSchema.name}
}}
=== Links ===
* [[:Category:${pageSchema.category}]]
* [[Concept:${pageSchema.category}]]
* [[:Template:${pageSchema.category}]]
* [[:Form:${pageSchema.category}]]
* [[List of ${pageSchema.pluralName}]]
<#if (pageSchema.getProperties().size() > 0)>
====Properties ====
<#list pageSchema.properties as property>
* [[Property:${property.name}]]
</#list>
</#if>
${sourcedocumentation}<br>
[[Category:PageSchema]]This Category has been ${generated}<br>