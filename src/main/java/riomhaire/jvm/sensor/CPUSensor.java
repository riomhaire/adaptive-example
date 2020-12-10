package riomhaire.jvm.sensor;

public class CPUSensor {

    public Double getLoad() {
        double load = 0;
        try {
            javax.management.MBeanServer mbs = java.lang.management.ManagementFactory.getPlatformMBeanServer();
            javax.management.ObjectName name = javax.management.ObjectName.getInstance("java.lang:type=OperatingSystem");
            javax.management.AttributeList list = mbs.getAttributes(name, new String[]{"ProcessCpuLoad"});

            if (list.isEmpty()) return 0.0;

            javax.management.Attribute att = (javax.management.Attribute) list.get(0);
            Double value = (Double) att.getValue();

            // usually takes a couple of seconds before we get real values
            load = value == -1 ? 0 : ((int) (value * 1000) / 10.0);
        }
        catch(Throwable th) {
            load = 0.0;
        }
        return load;
    }
}
