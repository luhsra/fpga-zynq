open_project BOARD_NAME_HERE_rocketchip_CHISEL_CONFIG_HERE/BOARD_NAME_HERE_rocketchip_CHISEL_CONFIG_HERE.xpr
reset_run synth_1
reset_run impl_1
launch_runs synth_1
wait_on_run synth_1
launch_runs impl_1 -to_step write_bitstream
wait_on_run impl_1
open_run impl_1
report_utilization -cells top/target/plic -file CHISEL_CONFIG_HERE_util_impl_plic.txt
report_utilization -cells top/target -file CHISEL_CONFIG_HERE_util_impl_rocket.txt
exit
