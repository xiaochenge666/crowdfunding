# 众筹基金 - System
基金筹集系统

项目总结：
在将把jsp改为thymeleaf时，后端配置模板引擎时，出现了一个问题
###问题1：
在配置文件SpringConfig中无法获取到webApplicationContext。而在配置thymeleaf模板引擎时，又必须依赖servletContext.
```java
 WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
````

在配ServletContextTemplateResolver时传出一个servletContext对象
```java

 ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(ServletContext servletContext);
```
在使用上边方法获取webApplicationContext时会获得一个空对象（具体原因等待后期研究）
```解决方式```：让配置类实现一个接口```ApplicationContextAware```重写里边的方法，在项目启动时，会调用实现该接口的子类中的该方法：
```java
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    
    }
```
可以通过该方法拿到```ApplicationContext```对象，该对象可以获取到```servletContext```对象

###问题2：
在使用thymeleaf语法时：注意区别以下两种写法
````html
//当前面访问过:http://localhost:8849/webui_war_exploded/admin/toLogin时再去访问以下：
<!--会出问题-->
th:action="@{admin/doLogin}"//访问时：	http://localhost:8849/webui_war_exploded/admin/admin/doLogin
<!--正常访问-->
th:action="@{/admin/doLogin}"//访问时真实路径：http://localhost:8849/webui_war_exploded/admin/doLogin
````
###问题3：
thymeleaf碎片的使用````

