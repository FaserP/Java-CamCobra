package LocalMonitoringStation;


/**
* LocalMonitoringStation/LMSPOATie.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from C:/Users/F/Workspace3/CamCobra_u1167937test/lms.idl
* Thursday, 28 April 2016 23:08:05 o'clock BST
*/

public class LMSPOATie extends LMSPOA
{

  // Constructors

  public LMSPOATie ( LocalMonitoringStation.LMSOperations delegate ) {
      this._impl = delegate;
  }
  public LMSPOATie ( LocalMonitoringStation.LMSOperations delegate , org.omg.PortableServer.POA poa ) {
      this._impl = delegate;
      this._poa      = poa;
  }
  public LocalMonitoringStation.LMSOperations _delegate() {
      return this._impl;
  }
  public void _delegate (LocalMonitoringStation.LMSOperations delegate ) {
      this._impl = delegate;
  }
  public org.omg.PortableServer.POA _default_POA() {
      if(_poa != null) {
          return _poa;
      }
      else {
          return super._default_POA();
      }
  }
  public String name ()
  {
    return _impl.name();
  } // name

  public void add_camera (SensorCamera.Sensor aSensor, String aZone)
  {
    _impl.add_camera(aSensor, aZone);
  } // add_camera

  public void record_camera (SensorCamera.Sensor aSensor, String aZone, SensorCamera.Image aReading)
  {
    _impl.record_camera(aSensor, aZone, aReading);
  } // record_camera

  public void raise_alerts (SensorCamera.Sensor aSensor, String aZone, SensorCamera.Image aReading)
  {
    _impl.raise_alerts(aSensor, aZone, aReading);
  } // raise_alerts

  public String[] get_zones ()
  {
    return _impl.get_zones();
  } // get_zones

  public SensorCamera.Sensor[] get_camera (String zone)
  {
    return _impl.get_camera(zone);
  } // get_camera

  public LocalMonitoringStation.Log[] retrieve_logs (String sensor, String zone)
  {
    return _impl.retrieve_logs(sensor, zone);
  } // retrieve_logs

  public LocalMonitoringStation.Alert[] retrieve_alerts (String sensor, String zone)
  {
    return _impl.retrieve_alerts(sensor, zone);
  } // retrieve_alerts

  private LocalMonitoringStation.LMSOperations _impl;
  private org.omg.PortableServer.POA _poa;

} // class LMSPOATie
