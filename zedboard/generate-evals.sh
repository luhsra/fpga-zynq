#!/bin/bash

set -e

timestamp=$(date '+%d.%m.%y_%H:%M:%S')
commit=$(cd ../rocket-chip && git rev-parse --short HEAD)
base="evals/eval_${commit}_${timestamp}"
echo $base

mkdir -p "$base"

HARTS_SYNTH="2 4 8 16 32"
HARTS_IMPL="2 4"
IRQS="8 16 32 64 128"

for h in $HARTS_SYNTH
do
    for i in $IRQS
    do
        unset FPGA_CONFIG
        config_str="EvalConfigH${h}I${i}"
        FPGA_CONFIG="$config_str" make synth_utilization
        eval_dir="$base/synth/${config_str}"
        gen_dir="zedboard_rocketchip_$config_str"
        mkdir -p "$eval_dir"
        mv -f "${config_str}_util_synth_plic.txt" "$eval_dir/util_plic.txt"
        mv -f "${config_str}_util_synth_rocket.txt" "$eval_dir/rocket_size.txt"
        cp -rf src/ "$gen_dir" "$eval_dir/"
        rm -f "src/verilog/Top.${config_str}.v"
        rm -rf "$gen_dir/"
    done
done

# implementation utilization can only
# be generated for < 4 harts and < 128 irqs
for h in $HARTS_IMPL
do
    for i in $IRQS
    do
        unset FPGA_CONFIG
        config_str="EvalConfigH${h}I${i}"
        FPGA_CONFIG="$config_str" make impl_utilization
        eval_dir="$base/impl/${config_str}"
        gen_dir="zedboard_rocketchip_$config_str"
        mkdir -p "$eval_dir"
        mv -f "${config_str}_util_impl_plic.txt" "${eval_dir}/util_plic.txt"
        mv -f "${config_str}_util_impl_rocket.txt" "${eval_dir}/rocket_size.txt"
        mv -f "${config_str}_timing_impl_plic.txt" "${eval_dir}/timing_plic.txt"
        mv -f "${config_str}_timing_impl_rocket.txt" "${eval_dir}/timing_rocket.txt"
        cp -rf src/ "$gen_dir" "$eval_dir/"
        rm -f "src/verilog/Top.${config_str}.v"
        rm -rf "$gen_dir/"
    done
done
