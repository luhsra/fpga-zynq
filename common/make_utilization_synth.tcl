open_project BOARD_NAME_HERE_rocketchip_CHISEL_CONFIG_HERE/BOARD_NAME_HERE_rocketchip_CHISEL_CONFIG_HERE.xpr
reset_run synth_1
launch_runs synth_1
wait_on_run synth_1
open_run synth_1
report_utilization -cells top/target/plic -file CHISEL_CONFIG_HERE_util_synth_plic.txt
report_utilization -cells top/target -file CHISEL_CONFIG_HERE_util_synth_rocket.txt
exit
