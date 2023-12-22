package ${package.Service};

import ${package.Entity}.${entity};
import ${superServiceClassPackage};
import java.util.List;
/**
 * <p>
 * ${table.comment!} 服务类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}<${entity}>
<#else>
public interface ${table.serviceName} extends ${superServiceClass}<${entity}> {

    /**
    * 查询任意数据
    * @param ${entity? uncap_first} 对象数据
    * @return 对象数组
    */
    List<${entity}> findAll(${entity} ${entity? uncap_first});

    /**
    * 修改任意数据
    * @param ${entity? uncap_first} 对象数据
    * @return 对象数组
    */
    int modifyAll(${entity} ${entity? uncap_first});
}
</#if>
