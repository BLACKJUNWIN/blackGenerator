package ${package.Mapper};

import ${package.Entity}.${entity};
import ${superMapperClassPackage};
<#if mapperAnnotationClass??>
import ${mapperAnnotationClass.name};
</#if>
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
/**
 * <p>
 * ${table.comment!} Mapper 接口
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if mapperAnnotationClass??>
@${mapperAnnotationClass.simpleName}
</#if>
<#if kotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
@Mapper
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {
      /**
      * 查询任意数据
      * @param ${entity? uncap_first} 对象数据
      * @return 对象数组
      */
      List<${entity}> selectAll(${entity} ${entity? uncap_first});

      /**
      * 修改任意数据
      * @param ${entity? uncap_first} 对象数据
      * @return 对象数组
      */
      int updateAll(${entity} ${entity? uncap_first});
}
</#if>
