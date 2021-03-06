package RegionalMonitoringCentre;


/**
* RegionalMonitoringCentre/_RMCStub.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ./idl/rmc.idl
* Friday, 29 April 2016 00:01:59 o'clock BST
*/

public class _RMCStub extends org.omg.CORBA.portable.ObjectImpl implements RegionalMonitoringCentre.RMC
{

  public void add_station (LocalMonitoringStation.LMS lms)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("add_station", true);
                LocalMonitoringStation.LMSHelper.write ($out, lms);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                add_station (lms        );
            } finally {
                _releaseReply ($in);
            }
  } // add_station

  public void remove_station (LocalMonitoringStation.LMS station)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("remove_station", true);
                LocalMonitoringStation.LMSHelper.write ($out, station);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                remove_station (station        );
            } finally {
                _releaseReply ($in);
            }
  } // remove_station

  public LocalMonitoringStation.LMS get_station (String station)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("get_station", true);
                $out.write_string (station);
                $in = _invoke ($out);
                LocalMonitoringStation.LMS $result = LocalMonitoringStation.LMSHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return get_station (station        );
            } finally {
                _releaseReply ($in);
            }
  } // get_station

  public String[] get_stations ()
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("get_stations", true);
                $in = _invoke ($out);
                String $result[] = RegionalMonitoringCentre.StationsHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return get_stations (        );
            } finally {
                _releaseReply ($in);
            }
  } // get_stations

  public String[] get_zones (String station)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("get_zones", true);
                $out.write_string (station);
                $in = _invoke ($out);
                String $result[] = LocalMonitoringStation.ZonesHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return get_zones (station        );
            } finally {
                _releaseReply ($in);
            }
  } // get_zones

  public SensorCamera.Sensor[] get_sensors (String station, String zone)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("get_sensors", true);
                $out.write_string (station);
                $out.write_string (zone);
                $in = _invoke ($out);
                SensorCamera.Sensor $result[] = LocalMonitoringStation.SensorsHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return get_sensors (station, zone        );
            } finally {
                _releaseReply ($in);
            }
  } // get_sensors

  public String[][] get_sensors_data (String station, String zone)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("get_sensors_data", true);
                $out.write_string (station);
                $out.write_string (zone);
                $in = _invoke ($out);
                String $result[][] = RegionalMonitoringCentre.Array1Helper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return get_sensors_data (station, zone        );
            } finally {
                _releaseReply ($in);
            }
  } // get_sensors_data

  public void register_user (RegionalMonitoringCentre.User aUser, String lms, String zone)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("register_user", true);
                RegionalMonitoringCentre.UserHelper.write ($out, aUser);
                $out.write_string (lms);
                $out.write_string (zone);
                $in = _invoke ($out);
                return;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                register_user (aUser, lms, zone        );
            } finally {
                _releaseReply ($in);
            }
  } // register_user

  public RegionalMonitoringCentre.Warning[] get_all_warnings (String lms, String zone)
  {
            org.omg.CORBA.portable.InputStream $in = null;
            try {
                org.omg.CORBA.portable.OutputStream $out = _request ("get_all_warnings", true);
                $out.write_string (lms);
                $out.write_string (zone);
                $in = _invoke ($out);
                RegionalMonitoringCentre.Warning $result[] = RegionalMonitoringCentre.WarningsHelper.read ($in);
                return $result;
            } catch (org.omg.CORBA.portable.ApplicationException $ex) {
                $in = $ex.getInputStream ();
                String _id = $ex.getId ();
                throw new org.omg.CORBA.MARSHAL (_id);
            } catch (org.omg.CORBA.portable.RemarshalException $rm) {
                return get_all_warnings (lms, zone        );
            } finally {
                _releaseReply ($in);
            }
  } // get_all_warnings

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:RegionalMonitoringCentre/RMC:1.0"};

  public String[] _ids ()
  {
    return (String[])__ids.clone ();
  }

  private void readObject (java.io.ObjectInputStream s) throws java.io.IOException
  {
     String str = s.readUTF ();
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     org.omg.CORBA.Object obj = orb.string_to_object (str);
     org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl) obj)._get_delegate ();
     _set_delegate (delegate);
   } finally {
     orb.destroy() ;
   }
  }

  private void writeObject (java.io.ObjectOutputStream s) throws java.io.IOException
  {
     String[] args = null;
     java.util.Properties props = null;
     org.omg.CORBA.ORB orb = org.omg.CORBA.ORB.init (args, props);
   try {
     String str = orb.object_to_string (this);
     s.writeUTF (str);
   } finally {
     orb.destroy() ;
   }
  }
} // class _RMCStub
