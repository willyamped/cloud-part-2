#!/bin/bash
ansible-galaxy collection install openstack.cloud:2.0.0
. ./openrc.sh; ansible-playbook mrc.yaml | tee output.txt