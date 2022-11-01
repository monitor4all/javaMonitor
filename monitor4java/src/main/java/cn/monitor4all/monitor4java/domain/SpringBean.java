package cn.monitor4all.monitor4java.domain;

public class SpringBean extends TimerWrapper{

    private String beanName;

    private String className;

    private String classLoaderName;

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassLoaderName() {
        return classLoaderName;
    }

    public void setClassLoaderName(String classLoaderName) {
        this.classLoaderName = classLoaderName;
    }

    @Override
    public String toString() {
        return "SpringBean{" +
                "beanName='" + beanName + '\'' +
                ", className='" + className + '\'' +
                ", classLoaderName='" + classLoaderName + '\'' +
                '}';
    }
}
