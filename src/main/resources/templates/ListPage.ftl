__NOCACHE__
{{#ask: [[Concept:${pageSchema.category}]]
<#list template.getFields() as field>
| ?${pageSchema.category} ${field.name}
</#list>     
}}
[[:Category:${pageSchema.category}]]