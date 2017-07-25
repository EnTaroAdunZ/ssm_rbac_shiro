package cn.etop.rbac.modules.service.Impl;
import cn.etop.rbac.common.util.PermissionUtil;
import cn.etop.rbac.modules.json.ZtreePermission;
import cn.etop.rbac.modules.mapper.PermissionMapper;
import cn.etop.rbac.modules.mapper.Permission_GroudMapper;
import cn.etop.rbac.modules.mapper.RoleMapper;
import cn.etop.rbac.modules.mapper.RoleToPermissionMapper;
import cn.etop.rbac.modules.model.Permission;
import cn.etop.rbac.modules.model.Permission_Groud;
import cn.etop.rbac.modules.model.Role;
import cn.etop.rbac.modules.model.RoleToPermission;
import cn.etop.rbac.modules.service.IPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @version V1.0
 * @Description:关于Permission业务逻辑实现类,ApplicationContextAware
 * @author: TingFeng Zhang
 * @date: 2017/7/13 15:11
 */
@Service
public class PermissionImpl implements IPermissionService{

//    @Autowired
//    private ApplicationContext ctx;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RoleToPermissionMapper roleToPermissionMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private Permission_GroudMapper permission_groudMapper;

    @Autowired
    private RoleServiceImpl roleService;

    @Override
    public List<ZtreePermission> getAllZtreeMsg(Long id) throws Exception {
        List<Permission> permissions = permissionMapper.selectAll();
        List<Permission_Groud> permission_groudMappers = permission_groudMapper.selectAll();
        List<ZtreePermission> ztreePermissions=new ArrayList<>();
        List<Permission> permissionWithRole = PermissionUtil.getPermissionWithRole(id, roleService);
        for(Permission_Groud permission_groud : permission_groudMappers){
            ZtreePermission ztreePermission=new ZtreePermission(permission_groud);
            ztreePermissions.add(ztreePermission);
        }
        for(Permission permission:permissions){
            ZtreePermission ztreePermission=new ZtreePermission(permission);
            for(Permission permission1:permissionWithRole){
                if(permission.getId()==permission1.getId()){
                    ztreePermission.setChecked(true);
                }
            }
            ztreePermissions.add(ztreePermission);
        }

        return ztreePermissions;
    }

    @Override
    /**   
    * @Description:获得当前所有权限
    * @param: 
    * @returnType:java.util.List<cn.etop.rbac.modules.model.Permission>
    * @Exception:
    */
    public List<Permission> list() throws Exception {
        return permissionMapper.selectAll();
    }



//    /**
//     //    * @Description:扫描带RequiredPermission注解的方法,并刷新权限保存到数据库
//     //    * @param:
//     //    * @returnType:void
//     //    * @Exception:
//     //    */
//    public void reload() throws Exception {
//        List<Permission> permissions = permissionMapper.selectAll();
//        Map<String, Object> beansWithAnnotation = ctx.getBeansWithAnnotation(Controller.class);
//        for(Map.Entry<String,Object> entry:beansWithAnnotation.entrySet()){
//            Class<?> aClass = entry.getValue().getClass();
//            Method[] declaredMethods = aClass.getMethods();
//            for(Method m:declaredMethods){
//                System.out.println(m.getName());
//                Annotation[] declaredAnnotations = m.getDeclaredAnnotations();
//                for(Annotation annotation:declaredAnnotations){
//                    System.out.println(annotation.annotationType().getName());
//                }
//            }
//        }
//    }


//    /**
//    * @Description:扫描带RequiredPermission注解的方法,并刷新权限保存到数据库
//    * @param:
//    * @returnType:void
//    * @Exception:
//    */
//    public void reload() throws Exception {
//        List<Permission> permissions = permissionMapper.selectAll();
//        Map<String, Object> beansWithAnnotation = ctx.getBeansWithAnnotation(Controller.class);
//        for(Map.Entry<String,Object> entry:beansWithAnnotation.entrySet()){
//
//            Object target = AopTargetUtils.getTarget(entry.getValue());
//            Method[] declaredMethods = target.getClass().getDeclaredMethods();
//            for(Method m:declaredMethods){
//                Annotation[] declaredAnnotations = m.getDeclaredAnnotations();
//                if(m.isAnnotationPresent(RequestMapping.class)){
//                    if(m.isAnnotationPresent(RequiredPermission.class)){
//                        Permission permission = PermissionUtil.methodToUrl(m);
//                        if(!permissions.contains(permission)){
//                            this.save(permission);
//                            String[] value = m.getAnnotation(RequestMapping.class).value();
//                            String[] value1 = m.getAnnotation(RequiresPermissions.class).value();
//                            System.out.println(value+":"+value1);
//                        }
//                    }else{
//                        String[] value = m.getAnnotation(RequestMapping.class).value();
//                        System.out.println(value[0]);
//                    }
//                }
//
//            }
//        }
//    }
//
    @Override
    /**
    * @Description:将权限保存到数据库
    * @param: [permission]
    * @returnType:void
    * @Exception:Exception
    */
    public void save(Permission permission) throws Exception {
        permissionMapper.insert(permission);
        permission.setId(permission.getId());
        Role role = roleMapper.selectByPrimaryKey(1L);
        RoleToPermission rtp=new RoleToPermission();
        rtp.setRole(role);
        rtp.setPermission(permission);
        roleToPermissionMapper.addItem(rtp);
    }

    @Override
    /**
     * @Description:新增权限
     * @param: [record]
     * @returnType:int
     * @Exception:Exception
     */
    public int insert(Permission record) throws Exception{
        Permission permission = permissionMapper.selectByExpression(record.getExpression());
        if(permission!=null){
            throw new RuntimeException("权限表达式已存在！请重新输入！");
        }
        Permission permission1 = permissionMapper.selectByName(record.getName());
        if (permission != null) {
            throw new RuntimeException("权限名已经存在！请重新输入！");
        }
        return   permissionMapper.insert(record);
    }

    @Override
    /**
    * @Description:通过ID删除权限
    * @param: [id]
    * @returnType:int
    * @Exception:Exception
    */
    public int deleteByPrimaryKey(Long id) throws Exception{
        return permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    /**
    * @Description:
    * @param: [record]
    * @returnType:int
    * @Exception:Exception
    */
    public int updateByPrimaryKey(Permission record) throws Exception{
        return permissionMapper.updateByPrimaryKey(record);
    }

    @Override
    /**
    * @Description:通过ID查找权限
    * @param: [id]
    * @returnType:cn.etop.rbac.modules.model.Permission
    * @Exception:Exception
    */
    public Permission selectByPrimaryKey(Long id) throws Exception {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    /**
    * @Description:查找权限名包括关键字的权限
    * @param: [keyWord]
    * @returnType:java.util.List<cn.etop.rbac.modules.model.Permission>
    * @Exception:Exception
    */
    public List<Permission> selectByKeyWord(String keyWord) throws Exception{
        return permissionMapper.selectByKeyWord(keyWord);
    }


//    @Override
//    /**
//    * @Description:容器启动后初始化权限
//    * @param: [applicationContext]
//    * @returnType:void
//    * @Exception:
//    */
//    public void setApplicationContext(ApplicationContext applicationContext) {
//        this.ctx=applicationContext;
//        try {
//            this.reload();
//        } catch (Exception e) {
//            System.out.println("权限初始化失败!!!");
//            e.printStackTrace();
//        }
//    }



}
