# LComponent
**LComponent** defines interfaces for a component to update data with, obtain value from and notify
actions, which are defined by 3 methods and 1 interface.

A component can be a simple component for example a Java class, or complex android custom view for
instance.

Component may not be isolated from others, it may receive data to get updated, return value if
others want, notify events to others.Moreover some times or in some situations, the instance of
component can not be obtained which leads to failing in connection with it.

But if a component **implements** the **LComponent**, the component **becomes available and friendly**.
