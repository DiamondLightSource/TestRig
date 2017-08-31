from pkg_resources import require
require('cothread==2.13')
from cothread import catools


def get_pv_values(pvs):
    return caget([pv.get_name() for pv in pvs])

def get_pv_value(pv):
    return caget(pv.get_name())

def set_pv_values(pvs, values):
    caput_sync([])

def set_pv_value(pv, value):
    caput_sync(pv.get_name(), value)

def caget(pv_names):
    return catools.caget(pv_names)

def caput_async(pv_names, values):
    catools.caput(pv_names, values)

def caput_sync(pv_names, values):
    catools.caput(pv_names, values, wait=True)