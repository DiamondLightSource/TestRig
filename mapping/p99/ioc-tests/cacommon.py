from pkg_resources import require
require('cothread==2.13')
from cothread import catools
from processvariable import *


def get_pv_values(pv_list):
    return caget(generate_name_list(pv_list))

def get_pv_value(pv):
    return caget(pv.get_name())

def set_pvs_to_defaults(pv_list):
    set_pv_values(generate_name_list(pv_list), generate_defaults_lists(pv_list))

def set_pv_values(pv_list, values):
    caput_sync(generate_name_list(pv_list), values)

def set_pv_value(pv, value):
    caput_sync(pv.get_name(), value)

def caget(pv_names):
    return catools.caget(pv_names)

def caput_async(pv_names, values):
    catools.caput(pv_names, values)

def caput_sync(pv_names, values):
    catools.caput(pv_names, values, wait=True)