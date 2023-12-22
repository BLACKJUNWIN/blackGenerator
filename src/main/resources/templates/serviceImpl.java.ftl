package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
/**
 * <p>
 * ${table.comment!} 服务实现类
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}<${table.mapperName}, ${entity}>(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass}<${table.mapperName}, ${entity}> implements ${table.serviceName} {
    @Autowired
    private ${table.mapperName} ${table.mapperName? uncap_first};


    /**
    * 查询任意数据
    * @param ${entity? uncap_first} 对象数据
    * @return 对象数组
    */
    @Override
    public List<${entity}> findAll(${entity} ${entity? uncap_first}){
     return ${table.mapperName? uncap_first}.selectAll(${entity? uncap_first});
    }

    /**
    * 修改任意数据
    * @param ${entity? uncap_first} 对象数据
    * @return 对象数组
    */
    @Override
    public int modifyAll(${entity} ${entity? uncap_first}){
     return ${table.mapperName? uncap_first}.updateAll(${entity? uncap_first});
    }
}
</#if>
