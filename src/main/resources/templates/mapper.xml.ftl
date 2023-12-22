<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package.Mapper}.${table.mapperName}">

    <#if enableCache>
        <!-- 开启二级缓存 -->
        <cache type="${cacheClassName}"/>
    </#if>
    <!-- 通用查询映射结果 -->
    <resultMap id="BaseResultMap" type="${package.Entity}.${entity}">
        <#list table.fields as field>
            <#if field.keyFlag><#--生成主键排在第一位-->
                <id column="${field.name}" property="${field.propertyName}"/>
            </#if>
        </#list>
        <#list table.commonFields as field><#--生成公共字段 -->
            <result column="${field.name}" property="${field.propertyName}"/>
        </#list>
        <#list table.fields as field>
            <#if !field.keyFlag><#--生成普通字段 -->
                <result column="${field.name}" property="${field.propertyName}"/>
            </#if>
        </#list>
    </resultMap>

    <!-- 通用查询结果列 -->
    <select id="selectAll" resultMap="BaseResultMap" parameterType="${package.Entity}.${entity}">
        select * from `${table.name}`
        <where>
            <#list table.fields as field>
                <#if field.propertyName?contains("time")||field.propertyName?contains("Time")||field.propertyName?contains("id")||field.propertyName?contains("Id")>
                    <if test="${field.propertyName}!=null">
                        and `${field.name}` = <#noparse>#{</#noparse>${field.propertyName}}
                    </if>
                <#else>
                    <if test="${field.propertyName}!=null and ${field.propertyName}!=''">
                        and `${field.name}` = <#noparse>#{</#noparse>${field.propertyName}}
                    </if>
                </#if>
            </#list>
        </where>
        order by `create_time` desc
    </select>

    <update id="updateAll" parameterType="${package.Entity}.${entity}">
        update `${table.name}`
        <set>
            <#list table.fields as field>
                <#if field.propertyName =="id">
                <#elseif field.propertyName?contains("time")||field.propertyName?contains("Time")||field.propertyName?contains("id")||field.propertyName?contains("Id")>
                    <if test="${field.propertyName}!=null">
                        `${field.name}` = <#noparse>#{</#noparse>${field.propertyName}},
                    </if>
                <#else>
                    <if test="${field.propertyName}!=null and ${field.propertyName}!=''">
                        `${field.name}` = <#noparse>#{</#noparse>${field.propertyName}},
                    </if>
                </#if>
            </#list>
        </set>
        where `id` = <#noparse>#{id}</#noparse>
    </update>
</mapper>
