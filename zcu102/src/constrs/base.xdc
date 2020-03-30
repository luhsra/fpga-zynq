set_property PACKAGE_PIN F21      [get_ports "CLK_125_N"] ;# Bank  47 VCCO - VCC3V3   - IO_L5N_HDGC_AD7N_47
set_property IOSTANDARD  LVDS_25  [get_ports "CLK_125_N"] ;# Bank  47 VCCO - VCC3V3   - IO_L5N_HDGC_AD7N_47
set_property PACKAGE_PIN G21      [get_ports "CLK_125_P"] ;# Bank  47 VCCO - VCC3V3   - IO_L5P_HDGC_AD7P_47
set_property IOSTANDARD  LVDS_25  [get_ports "CLK_125_P"] ;# Bank  47 VCCO - VCC3V3   - IO_L5P_HDGC_AD7P_47
create_clock -add -name CLK_125_P -period 8.00 -waveform {0 4.0} [get_ports CLK_125_P]
