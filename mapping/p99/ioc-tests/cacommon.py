from pkg_resources import require
require('cothread==2.13')
from cothread import catools


def caget(pv_names):
    return catools.caget(pv_names)

def caput_async(pv_names, values):
    catools.caput(pv_names, values)

def caput_sync(pv_names, values):
    catools.caput(pv_names, values, wait=True)