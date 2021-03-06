<!--  Plantuml  -->
<uml>
title ${pageSchema.category}
note as ${pageSchema.category}DiagramNote
Copyright (c) 2015 BITPlan GmbH
[[http://www.bitplan.com]]
end note
<#list pageSchema.getTemplates() as template>
note as ${template.name}Note
${pageSchema.umlDocumentation}
end note
class ${pageSchema.category} <<Category>> {
<#list template.getFields() as field>
' ${field.category!"no category"} ${field.name} ${field.label}
<#if field.category??>
<#else>
${field.property.type} ${field.label}
</#if>
</#list>
}
${template.name}Note .. ${pageSchema.category}
</#list>
<#list linkedSchemas as linkedSchema>
${pageSchema.category} -- ${linkedSchema.category}
</#list>    
skinparam classBackgroundColor white
skinparam classBorderColor #FF8000
skinparam classFontColor black
skinparam classFontSize 12
skinparam classFontName Arial
skinparam NoteBorderColor #FF8000
skinparam NoteBackgroundColor #FFFFF0
hide circle
</uml>