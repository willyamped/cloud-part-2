#!/bin/bash

ansible-galaxy collection install openstack.cloud:2.1.0
. ./openrc.sh; ansible-playbook -vv mrc.yaml | tee output.txt