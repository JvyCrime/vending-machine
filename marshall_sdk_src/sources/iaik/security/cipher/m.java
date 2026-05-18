package iaik.security.cipher;

import iaik.utils.CriticalObject;
import iaik.utils.Util;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;

/* JADX INFO: loaded from: classes.dex */
final class m extends AbstractC0030k {
    static Class b;
    private static final int[] o = Util.decodeIntArray("MPtA1J+g/wtr7M0vPyWMeh4hPy+cAE3TYAPlQM+fyUm/1K8niLu9teIDQJCY0JZ1bmOg4BXDYdLC52YdItT/jihoO2/Af9BZ/yN5yHdfUOJDw0DT3y+GVoh8pBqi0r0tocng1jRsSBlht22HIlQPLyq+MuGqVBZrIlaOOqLTQdBm20DIp4Q5LwBN/y8tudLel5Q/rEqXwdhSdkS3tfQ3p7gsuu/XUdFZb/fw7VoJeh+Ce2jQkOz1LiKwwFS8jlk1S20vf1C7ZKLSZkkQvuWBLbczIpDpOxWftI7kEUv/NF39RcJArTGXP8T20C5V/IFl1bHKraGsLa6i1LdtwZsMUIgiQPIMbk84pOS/109bonJWTB0vxZxTGblJ41SwRmn+sbariscTWN1jhcVFEQ+TXVdTitVqOQST5j034CpU9rM6eH1fYnagtRmm/N96QiBqKfnU1fYbGJG7cideqlCBZziQEJHGtQXrhMfLjCrXWg+HShQnotGTayrShq+qVtKR14lDYEJcdQ2Ts54mGHGEyWwAsy1z4rsUoL68PFRiN3lkRZ6rPzKLgncYz4JZos6mBO4ALon+eOY/qwlQMl/2woE4PwVpY8XIdsta1tSZdMnKGA3POAeC1cf6XPaKwxURNeeeE0fakdD0D5CGp+JBnjE2YkEFHvSVqlc7BEqAXY1UgwDQADIqPL9kzd+6V6aOdcY3K1Cv00GnwTJ1kVoL9WtUv6srCxQmq0zJ10SczYL3+/Jlq4XF8xtV25Sq1OMkz6S9Py3qo+KeIE0CyL0lrOrfVbPVvZ6Y4xIxsirVrWyVQynerb5FKNhxD2mqUckPqnhr9iJRPx6qUaebKtNEzHtaQfDTfPutGwaVBUHs5JG0wzLmAyJo1MlgCszOOH5tv2uxbGpw+3gNA9nJ1N853uAQY9pHNvRkWtMo2LNHzJZ1uw/DmFEb+0/7zDW1i89q4R8KvL/F/kqnCuwQrDlXCj8ERC9hiLFT4Dl6Llcny3mc60GPHKzWjSrTfJYBdcudxp3/CcdbZfDZ20DY7A53eUdE6tSxHDJ03STLnn4cVL3wEUT50iQOsZZ1s/2jrDdV1Hwnr1HIX01WkHWWpbsV5lgDBPDKBCzxARo36o2/qts1uj5KNSb/oMN7TQm8MG7ZmKUmZlZI9yX/XladDO1j0Hxjss9wC0Xh1epQ8YWpKHKvH72n1CNIcKeHC/MtO015QuBBmAzQ7ecmRw24+IGBTEdNatd8DF5c0SMZWTgbcpj10vTbq4OGU24vHiODcZyevZHgRppWRW7cOSAMIMjFcZYr2hzh5pb/sUGrCHzKibkaaeeDAsxIQ6L3xXlCnvR9QnsWnFrJ8Endjw8AXIFlvw==");
    private static final int[] p = Util.decodeIntArray("HyAQlO8Lp1tp489+OT9DgP5hz3ruxSB6VYiclHL8BlGtp+95Th1yNdVaY87eBDa6mcQw718MB5QY3Nt9odbv86C1L3tZ6DYF7hWwlOn/2QncRACG75REWbqDzLPgw8370dpBgTsJKrH5l/HBpebPewFCDdvk5+9bJaH/QeGA+AYfxBCAF5vuetN6xqn+WDCkmN6Lf3foP055kpJpJPqfe+ETyFusxACD11A1JffqYV9iFDFUDVVLY11oESHIZsNZPWPPc87iNMDU2H6HXGcrIQcfYYE592J/Nh4whOTrVztgL2Sk1jrNnBu8RjWegQMtJwH1DJmEerSg4995umzzjBCEMJQlN6le9G9v/qH/Ox8gjPtqj0WMdNngoidOxzo0/IhPaT5N6N/vDgCINVlkjYpFOIwdgENmch2b/aWGhLvoJWMzhE6CEhKNgJj+0z+0zigK4Sfhm6XVpsJS5JdUvcXWVd3rZnBkd4QLTaG2qAGE2yap4LVnFCHwQ7fl0FhgVPAwhAZv9HKjGqFT2txHVbViXb9oVhvmg8prlC1u0jvszwHbptPQuraAPVyvd6cJM7SjTDl7yNZe4iuVXw5TBIHtb2Eg50NktF4TeN4YY5uIHKEiuWcm0YBJp+git9p7XlUtJVJy0jd50pUcxg2JTEiMtAIbpP5bpLCfaxyoFc+iDDAFiHHfY7neL8sMxsnpC+7/U+MhRRe0VCg1n2MpPO5B5yluHS18UARShh5mhfPzNAHGMKIslTGnCFBgkw8Tc/mEF6EmmFnsZFxEUsh3qc3/M6agKxdBfLrZoiGAA29Q2ZwIyz9IYcJr12Vko/argDQmdiWnXnvk5tH8IMcQ5s3wtoAXhE07Me74TX4IJOQsy0nrhGo7ro/3eIjuXWD2evdWcy/dXNuhFjHBMPZvQ7P67FQVf9f674V5zNFS3ljbL/1ejzLOGTBq+XoC8D74mTGa1cJC+g+n4+uwxo5JBrjaIwyAgjAo3N7zyNNfsXEIihvIvsDFYGGjyei8qPVNxy/v+iKCLpmCxXC02NlOiYscNLwwHhbmJzvpebD/6qZh2bjGALJIabf/zj8I3Cg7Q9r2Wvfhl5h2GbcvjxybpNyGN6AWp9Oxn8OTt6cTbuvGvMY+GlE3Qu9oKLxSA2XWLWp3qzUn7UuCH9IWCVxuLtuS8vte6inLFFiS9ZFYT39Ug2l7JmeozIUZYEiMS6zqgzhg1A0j4PlsOH6KCubSSbKEYAzYNXMd3LHGR6xMVuo+vYGzIw6rsGQ4vIfwtbH6j16is/wYRkIKA2t6T7CJvWSdpYmjRUFeXAODIz5dO7lD15Vyfm3QfAbf3x5sbMTvcWClOXO/vnCDh3YFRSPs8Q==");
    private static final int[] q = Util.decodeIntArray("je/CQCX6XZ/rkD2/6BDJB0dgf/82n+RLjB/GRK7OypC+sfm/7vvK6ujPGVBR3weukg6IBvCtBUjhPI2DknAQ1REQfZ8HZH25suPk1D1PKF65r6gg+t6C4KBnJouCcnkuVT+ywEia4ivU75eUEl4/vCH//O6CWxv9klXF7RJXokBOGoMCuuB//1KCRueOVxQOM3P3v4yfgYim/E7oyYK1pajAHbdXn8JkZwlPMfK9P19A//fBH7eN/I5r0sFDe+WbmbA9v7XbxktjjcDmVYGdmaGXyBxKAS1uxYhKKMzDb3G4Q8ITbAdD8YMJiTwP7d1fL3/oUNfAf34CUH+/WvuaBKdH0tAWURkur3C/PljDE4BfmDAucnzDxAoPtAIPf++CjJb9rV0sKq6O6ZpJUNqIuIQn9KAerFeQeW+0SYJS3BXvvX2bpnJZfa2oQNhF9UUE+l10A+g+wwVPkXUaklZpwiPv6UGpA/EuYCcN8gJ25LaU/WV0knmFsoJ228sCd4F2+K+RjU5I956PYW3f4p2EDoQvfYM0DOXIlru2gpO0sUjvMDyrmE+vKHefr5uS3FYNIk0eIIQ3qoh9KdyWJ1bT3IuQfO61H9JA58B84+VmtKHD6WFePPggnWCU0ePNnKNBXHZGDgDqmDvU1niB/UdXLPds7dm9qCKcEn2tqkOKB04fl8CQCBvbipOgfr65OMoVl7A8/z3CwPiNGrLsZDgOUWjMe/vZDyeIEkkBgV3l/9TdfvhqdqLiFLmkA2iSXZWPSzn/+ro5rumk/9ML+veTO21JhiMZPLz6J2J1RYJc9HphvYug0R5C0c6tBPQSfqOSEEKNt4JyqXKScMSoEn3lCyhbocg8YvRPNcDqpegF0jFCiSn7tPzfgk+2alMOfcFbHwgfqxCGGK78/Qht+f8oiWlLzBEjalyuEt7KTSw/jMXS0C3++O9YluTPUtqVFVtnSUpIjLm2qAxcj4K8idNrRTpglDfsAMmpRHFSUwqHS0nXc7xAfDRnHAJxfvZP61U2otAv/9K/YMTUPwPAULTvbQdHjNEAbhiIouU/Vbnm1LyiBIAWl1c4M9cgfWfeD489cvh7M6vMTzN2iMVdewCmsJR7AAFXAHXS+buI+IlCAZ5CZKX/hWMC4HLb2SvulxtpbqIv3l8IriuvemFt5cmHZ88f69Jh78jC8awlccyCOcJnIUy4seWD0bfcPmJ/EL3O+QpcOA/wRD1gbm3GYFQ6SVcnwUgr6YodirQXOCDhviSvltoPaEWEJZmDO+VgDUV9KC+TUIM0s2LZHREgK22NoGQrHjGcMFoAUrzmiBsDWIr3uu/VQULtnKQxXBGDMj7F3+9GNqEzxQHp01Mc7jU3gw==");
    private static final int[] r = Util.decodeIntArray("nbMEIB+26d6nvnvv0nOimEpPe9tkrYxXhVEEQ/oCDtF+KHr/5g+2YwlfNaF56/Eg/QWdQ2SXt7HzZB9jJB5K3ygUf19PorjNyUMAQAzDIiD90wswwKU3Tx0tANkkFHsV7k0RGg/KUWdx/5BMLRlf/hoFZF8ME/7+CBsIygUXASGAUwEA6D5e/qya9Ph/5ycB0rjuXwbfQmG7npuKcpPqJc6E/9/1cYgBPdZLBKJvJjt+1IQAVH7r5kRtTKBs89b1Jkmr366gx/U2M4zBUD9+k9N3IGERtjjhclAOA/gOsrur4FAu7I133leXHoHhT2dGyTNUAGkgMY8IHbuZ/8MEpU01GAV/PVzjpshmxl1bzKna7G/qn5JvkZ9GIi85kUZ9pb9tjhFDxE9DlYMC0CFO6wIgg7g/thgMGPiTHigWWOYmSG4+i9eKcHR35MG1BuB88y0KJXkJiwLk6ruBKBI7I2nerTgVdMoW34cbYiEcQLelGp75ABQ3ewQeisgJEUADvVnk0uPRVtVP6HbVL5GjQFV76N4A6uSnDOXC7E20u6bnVr3/3TNprOwXsDUGVyMnma/IsFbIw5FrZYEcXhRhGW6Fy3W+B8ACwjJVd4k/9Oxbv8kt0Ow7JbeAGreNbTskIMdj78NmpfycOCiACs4yBarJVIrsodfHBBr6Mh0WYlpnAZAsm3V6VDHUd/eRJrAxNsxv28cLi0bZ5mpIVuVaeQJqTOtSQ37/L492tA35gKWGdM3j7doE6xepvgQsGPTft3R/nasq97Tvw00gLglrfBdBolTltqA1IT1C9iwcfCZhwvUPZVLa+dLCMfglEw9p2BZ/ogQY8sgAGpamDRUmq2MxXCFeCnLsSbr+/Rh5CNmNDb2GMRFwpz6bZAzMPhDX1crTtgyuw4j3MAHhbHKK/3Hq4qEfmvNuz8vRL8HehBesB75ry0Sh2IubD1YBOYjDscUvyrS+Mc3YeCgGEqOk4m995TJY/X620B7pACSt/8L0mQ/FlxGqxQAde5WC5efSEJhz9gBhMJbDLZUhraEh/ymQhBV/u5d/r56z2ynJ7Spc4qRlpzDzLNCqP+iKXMCR1J4s5wzkVKnWCs2GAV8ZGXcHkQPeoDr2eKhWXt7jVt8h8Fy+i3Xjh7PFBlG4pcPv2O620uUjvnfCFUUpL2nv36/mevv0cMSy8+DrW9bMmHY55EYMH9qFOBmHgy/KAHNnqZFE+ClrKZ5JL8KVkma+q7Vnbmmb093a334FL9slcBwbXlHu9lMk5mr842wDFswEhkQhPrfcWdB5ZSkfzNb9Q0GCOXmTK832tlfDTU7f0oJ65SkMPLlTa4UeIP6YM1V+E+zwsNP/s3I/hcXBCu9+0g==");
    private static final int[] s = Util.decodeIntArray("fskMBCxudLmbDmbfpjN5Ebhqf/8d01j1RN2dRBcxFn8I+/H65/URzNIFGwBzWroAKrci2Dhjgcus9iQ6ab79euai53/wxyDNxElIFsz1wYA4hRZAFbCoSOaLGMtMqt7/X0gKAQQSsqolmBT8QdDv4k5AtI0kjrb7jboc/kGpmwIaVQoEuo9ly3JR9OeVpRclwQbs15elmArFObmqTXn+avLz92Nor4BA7QyeVhG0lYvh61qIhwnmsNfgcVZOKf6nY2blLQLRwADErI4Fk3f1cQwFNypXhTXyImG+AtZCoMnfE6KAdLVb0mghmcDUIeXsU/s86Mit7bMoqH/JPZWZgVwf+QD+ONOZDE7/CwYkB+qqL0+xT7lpdpDHlQWwqKd071Wh/+WcosKmti0n5mpCY99lAB8OxQlm391VvCneBlWRHnOaF6+JdTLHkRyJ+JRoDQHpgFJHVfQDtjzJDMhEsrzz8KqHrDbp5Tp0JgGz2CsannRJZO4tfs3bsdoByUkQuGi/gA0m8/2TQu3nBKXChGNnN7ZQ9bYW8kdm447KNsETbgXb/vGDkfuIejfW5/fUx/t9yTBj/N+29Yne7ClB2ibkZpW3VmQZ9lTvxdCNWLdIklQBwbrLf+X/VQ+2CDBJW7XQ6IfXLlqram7hIjpmzsYr882eCIX5aMs+RwhsAQ+iHegg0Ytp3vP2V3f6AsP2QH7aw8uz1VAXkwhNsNcOugqzeNXZUfsM3tfaVkEku+SUygtWD1dV0eDh5W5hhLW+WAokn5T3S8DjJ4iOn3tVYcPcAoAFaHcVZGxr10SQTbNmtPCjwPFkiml+1a9J6S/2MJ43Tyy2NWqFgIVzSZH4QHbwrgIIO+hNKEIcmkRIlAZzbky4wQkpEIvJX8Z9hpz0E09hby53EY2zGyvhqpC0cjyl1xd9Fhu6nK2QEK9GK6Kf5FnSRdNFWdny2hPbxlSH8+T5ThdtSG8JfBPqYx2lx0Rfc4IXVoP0zcZql3C+Aoizzc9ybl3S8yCTYHlFm4ClvmDi26nCMQHrpTFcIk5C8hxcFXL2chssGtL/84wlQE4yTtcvQGe3/QUjE45co7x43A/WbnWSIoN4TWsXWOuxbkQJT4U/SB2H/P6ue3e1/3aMIwK/qvR1Vl9GsCorCSgBPTj19wyoHzZSr0qKZtXnwN87CHSVBVEQG1rXqPYe1a1s9uR5IHWBhNDO+mWI975YSgRoJg/2+POgnH9wU0aroFzpbCjhdu2ja6wwfzdoKdKFNg+pF+P+KiS3l2f1qWsg1s0llWj/Hr91VUQs8Z8GvvngZZruuUkdNAEHGLswyrjoIv4ViFcJg3UOYknaYn5VXnb/qLFTRUZtR94I7+nn1A==");
    private static final int[] t = Util.decodeIntArray("9vqPnSysbOFMo0hn4jN/fJXbCOcBaEO07O1cvDJVU6y/nwlg36Hi7YPwV51j7Ya5GramuN5evjnzj/cyiYmxODPxSWHAGTe99QbG2uRiXn6jCOqZTiPjPHnL18xIoUNnoxSWGf7JS9WhFBdK6qAYZqCE2y0JqEhvqIhhSikAr5gBZlmR4ZkoY8jzDGAueO880NUZMs8P7BT3ygfS0Kggcv1BGX6TBaaw6Gvj2nS+0803LaU8TH9ESNq11EBtug7DCDkZp5+67tlJ28+wTmcMU1w9nAFkvblBLA5jarp92c3qb3OI5wvHYjXymttcTN2N8NSNjLiBU+IIoZhmGuLqyChMr4mqkoIjkzS+Uzs6Ib8WQ0vjmuo5Bu/ow274kM3ZgCJtrsNApKPffpwJppSoB1t8XswiHbOmmmmgL2iBilTOsilvU8CEOv6JNlUlv+aKtGKKvM8iLr8lrG9IqamTh1O922Xnb/vn6Wf9eAupNWOONCvB6KEb6UmAdA3ICH38jeS/maERAaB/03l12lomwOgfmU+VKM2J/TOf7bh4NL9fBEVtIiWGmMnEyDstwVa+T2KNqlf1XsXiIgq+0pFuv07HW5Uk8sPAQtFdmc0Nf6B7bif/qNyK8HNFwQb0HiMvNRYjhubqiSYzM7CUFX7G8jcrdK9pJXPk6anYSPMWAok6Yu8dp4fiOPOl9nZ0NkhTIJUQY0V2aY22+tQHWSr5UDb3NSNM+26HfaTOwGwVLarLA5aoxQ3+XfzXB6sJIcQvid/wu1/ivnhEj08zdUYTySsF0I1IudWF3ASUQcgJj5t97eeGw5ozc0JBAAVqCRdRDvPIpokActYoIHaCqan3vr8yZ53UW1t1s1P9AMuw41iDDyIKH4+yFNNyzwjMPEoTjPYxZgYch76IyY+IYGLjl0fPjnq2yFKDPMKs+z/AaXZOjwJSZNgxTdo4cOMeZlRZwQkI8FEwIaVsW2i3gi+KoDAHzT50cZ7v3IcmgQczQNR+Qy/ZDF7CQYgJKGz1ktiRCKkw9pV+8wW3+/+9wmbpb2/krJixc+zAvGC0KpU0mNr7oa4SLUvXNg8l+quk8/zr4paRIyV/DD2TSK9JNhQAvOiBb0o4FPIAo/lAQ5x6VMK8cE9X2kHn+cJa0zpU9KCEsX9VBVk1fL7tvRXIf5fFq7pax7W29t6vOkecOlMC2iVlPX5qVCaNSVGkd+pQF9Vb19JdiEQTbHYEBKjIuOWhIbgakopg7Vhpl8VblursmRspk1kTAf238QiOjfqatvb1O0y/n0pd46vmBR01oOHYVdNrTPH1RO3rsOk1JL67j72i12LPSckvVDi18zFxKKRUSDkpBaZbHbiFHJe91nXPLw==");
    private static final int[] u = Util.decodeIntArray("heBAGTMr9WdmLb//z8ZWkyqNf2+rm8kS3mAIoSAo2h8CJ7znTWQpFhj6wwBQ8YuCLLLLEbIy51xLNpXysocH3qBfvPbNQYHp4VAhDOJO8b2xaMOB/eTniVx5sNgei/1DTUlQATi+Q0GRPO4dkqecPwiXZr667q30Eoa+z7bqyxkmYMIAdWW95GQkH3qCSNypw7OtZigTYIYL2N+oNW0c8hB3ib6zsunOBQKqjwvANR4Wa/Uq6xL/guNIaRHTTXUWTns6/19DZxuc9uA3SYGsgzNCZs6Mk0G30NhUwMs6bIhHvCgpRyW6N6Zq0it61h8eDFy6+kQ38Qe255liQtLYFgqWEojhpcBuE3SeZ3L8CBqx0Tn3+Vg3Rc8Z31i+w/dWwG66MAchGyRFwogpyV4xf7yOxRE4vEbpxub6FLroWEqtTrxGRo9Qi3gpQ1/xJBg7gh26n6/2D/TqLE5tFuOSZJJUSosAm0/Dq6aM7ZrJb3gGpbeasoVubhrsPKm+g4aIDggE6VXxvlbn5TY7s6HyXffeu4Vh/gM8FnRiMzwDTCjabQx0earFbDzk4a1R8MgCmPjzWhYmpJ/u2CspHTgv4wxPuZq7Mld4PsbZe253pqnLZYtc1FIwxyvRQItgwD63uQaNeKM3VPT0MMh9yKcTArltjDLr1Oe+voudLXl5+wbnIlMIi3XPdxHvjaTgg8hYjWt4b1pjF6b6XPegXdoAM/KOv7D1ucMQoOrCgAi5dnqj2dKwedNCFwIacY2axjNqJxH9YEOAUOMGmQioPX/txIJtK+9O64R2SI3PJTbJ1WYo505BwmEKyj1Jqc+647nftl+N5pKur2Q6x9XmnqgFCfIrAX2kFz9w3R4WwxXg1/lQsbiHK59P1WJauoJqAXliLsAbnBVIiqnXFudAQAVaLJPSmiLjLb+aBYdFuTRT3B7WmSluSWz/bxyfSYbf4u0HuHJC0Rnefq4FPlYaFa1vjGZibBxxVMJM6ggrKpPrKTkX3LDwWNTyrp6ilPtSz1ZMmIP+Zi7EBYF2OVPDAdZpLtOgwQih5xYO5PLfpmk+0oV0kEaYTCsO3U91dlZdOTN4oTIjTz0yHF3D9eGUSyaTAcefAi88mX5+Xk+VBD/6+712960OKWaT9D0fzm/GHkW+07WrNPcr+bcbBDTATnK1Z1WSoz21IpMBz9Kof2Cut2cYFDhrMLzDPTigwH39Fgbyw2NRm1id05BUefjmHLjWR5f9Yanqd1n0LVdTnVaaWM/oTmOtRi4beGWA+H7zgXkUkdpV9ECiMPPRmI81tuMY0j/6ULw9QPAhw8C9rklYwkxRjzayhLHTcA/tzoOHjdra8qJ5x5TgG+iQcW9LlUuKow==");
    private static final int[] v = Util.decodeIntArray("4hYwDbvd//yn69q9NWSAlXeJ+LfmwRIbDiQWAAUs6LURqc+w5ZUvEeznmQqThtF0KkKTHHbjgRGxLe86N93d/N6a3rEKDMMsvhlwKYSgCUC7JDoPtNE3z7ROefAEnu39CxWhXUgNMWiLu95aZp3tQsfs6DE/j5Xnct8ZG3WAMw2UB0JRXH3N+qu+bWOqQCFkswHUCgLn0cpTVx2uejGCohKo3ez9qjNdF29D6HH7RtQ4EpAizpSa1LhHaa2WW9higvPQVWb7l2cVuAtOHVtHoEz94G/CjsS4V+hybmR6ePyZhl1EYIvVk2wgDgM53F/2XQsAo65jr/J+i9YycBCMDLvTUEkpmN8EmAz0Kptt9JGeft1TBpGFSFjLfgc7dO8uUi//sdJHCMwcfifNpOshWzzx0uIZtHo4Qk92GDWFYDmdF97nJ+s15smv9ns2uvW4CcRnzcGJELHhHb97Bs0a+HFwxggtXjNU1N5JWmTG0Aa8wMYsPdANs3CPjzR31RtCJk9iDyS40r8VwbeeRqUlZPjX5U4+N4FgeJXNpYWcFaXmRZeIw3vHX9sHugwGdqOrfyKbHjGELnskJZ/X+L70coNf/Lht9MHylvWxlf0K8Pyw/hNM4lBtPU+bEuryFfIloiNzb5+0xCgl0El5NMcT+MRhgYfqem6YfNFu/BQ2h2zxVEEHvt7uFFbpryegSqRBPPfImZLsuubdZwFtFRaC66hC7t/9umC08ZB7dSDjAw8k2MKe4TlnO++mP7hxhzBUtvLPO58yZELLFaTMsBpFBPHkfY2EShvluuff3ELL2nDNfa4KV+hbetU/WvYgz02MzqTUKHnRMKQ0huv7M9PN3HeFO1M37/y1xQaHeOWAs+ZOaLj0xcizfg2AnqI5j+t8EypPlEO3lQ4v7n0cIjYTvd0GyqI335MrxCSCiazz68NXFfa37zR43fJnYW/BSMvkkFKBXl5BD6u0iiRlLtp/pOh7QOTpjqCEWInp4e/TkPzdB9Nb20hWlDjX5bJXcgEBcw7evFtkMROUkX5PUDwvumRvEoJ1I9JK4HeWlfnBeo96WyEh0Ye4likmOk26UQzfgfR8n60RY+3qe1llGgBybhFAMJIA2m13SgzdYa0fRgNgW9+wnu3DZCLr5qjO59KKoOc2oFVkprkQhTIJx+uPNy3nBcqJUVcP3wmCK71pGmyqEuTyh0UcD+D2ono62kgZTPF2Tw13HCtnzbFWNQ2DhFk4+g9COZ7zNpl7Bw6ECT1KqT5hg2DYex+piwwRSTgs6XYlpQYU0bcOJSRLDHaDR1iejYINIFnRpGa7HvjaCoIE8ZEwum5OwJkmUWQe5yMNULKtgOruaAGNsqKD6ov1ng==");
    private int[] c;
    private int m;
    private int n;

    m() {
        super("CAST128", 1);
        this.c = new int[32];
    }

    static Class class$(String str) {
        try {
            return Class.forName(str);
        } catch (ClassNotFoundException e) {
            throw new NoClassDefFoundError(e.getMessage());
        }
    }

    @Override // iaik.security.cipher.t
    void a(int i, Key key, AlgorithmParameters algorithmParameters, SecureRandom secureRandom) throws InvalidKeyException, InvalidAlgorithmParameterException {
        if (algorithmParameters == null) {
            a(i, key, (AlgorithmParameterSpec) null, secureRandom);
            return;
        }
        try {
            Class clsClass$ = b;
            if (clsClass$ == null) {
                clsClass$ = class$("iaik.security.cipher.CAST128ParameterSpec");
                b = clsClass$;
            }
            a(i, key, algorithmParameters.getParameterSpec(clsClass$), secureRandom);
        } catch (InvalidParameterSpecException unused) {
            super.a(i, key, algorithmParameters, secureRandom);
        }
    }

    @Override // iaik.security.cipher.AbstractC0030k
    void a(int i, byte[] bArr) throws InvalidKeyException {
        byte[] bArr2;
        m mVar = this;
        int length = bArr.length;
        if (length < 5 || length > 16) {
            throw new InvalidKeyException("Key must be between 40 and 128 bit long!");
        }
        mVar.n = length << 3;
        if (length == 16) {
            bArr2 = bArr;
        } else {
            bArr2 = new byte[16];
            System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        }
        if (length <= 10) {
            mVar.m = 12;
        } else {
            mVar.m = 16;
        }
        int i2 = ((bArr2[0] & 255) << 24) | (((bArr2[1] & 255) & 255) << 16) | (((bArr2[2] & 255) & 255) << 8) | (bArr2[3] & 255 & 255);
        int i3 = bArr2[4] & 255;
        int i4 = bArr2[5] & 255;
        int i5 = (i4 & 255) << 16;
        int i6 = i5 | (i3 << 24) | (((bArr2[6] & 255) & 255) << 8) | (bArr2[7] & 255 & 255);
        int i7 = bArr2[8] & 255;
        int i8 = bArr2[9] & 255;
        int i9 = bArr2[10] & 255;
        int i10 = bArr2[11] & 255;
        int i11 = (i7 << 24) | ((i8 & 255) << 16) | ((i9 & 255) << 8) | (i10 & 255);
        int i12 = bArr2[12] & 255;
        int i13 = bArr2[13] & 255;
        int i14 = bArr2[14] & 255;
        int i15 = bArr2[15] & 255;
        int i16 = ((i14 & 255) << 8) | (i12 << 24) | ((i13 & 255) << 16) | (i15 & 255);
        int i17 = i7;
        int i18 = i9;
        int i19 = i12;
        int i20 = i15;
        int i21 = 0;
        while (i21 < 32) {
            int[] iArr = s;
            int i22 = i2 ^ iArr[i13];
            int[] iArr2 = t;
            int i23 = i22 ^ iArr2[i20];
            int[] iArr3 = u;
            int i24 = i23 ^ iArr3[i19];
            int[] iArr4 = v;
            int i25 = (i24 ^ iArr4[i14]) ^ iArr3[i17];
            int i26 = i25 >>> 24;
            int i27 = (i25 >> 16) & 255;
            byte[] bArr3 = bArr2;
            int i28 = (i25 >> 8) & 255;
            int i29 = i21;
            int i30 = i25 & 255;
            int i31 = iArr4[i18] ^ ((((i11 ^ iArr[i26]) ^ iArr2[i28]) ^ iArr3[i27]) ^ iArr4[i30]);
            int i32 = i31 >>> 24;
            int i33 = (i31 >> 16) & 255;
            int i34 = (i31 >> 8) & 255;
            int i35 = i31 & 255;
            int i36 = ((((i16 ^ iArr[i35]) ^ iArr2[i34]) ^ iArr3[i33]) ^ iArr4[i32]) ^ iArr[i8];
            int i37 = i36 >>> 24;
            int i38 = (i36 >> 16) & 255;
            int i39 = (i36 >> 8) & 255;
            int i40 = i36 & 255;
            int i41 = ((((i6 ^ iArr[i39]) ^ iArr2[i38]) ^ iArr3[i40]) ^ iArr4[i37]) ^ iArr2[i10];
            int i42 = i41 >>> 24;
            int i43 = (i41 >> 16) & 255;
            int i44 = (i41 >> 8) & 255;
            int i45 = i41 & 255;
            int[] iArr5 = mVar.c;
            int i46 = i29 + 1;
            iArr5[i29] = (((iArr[i37] ^ iArr2[i38]) ^ iArr3[i35]) ^ iArr4[i34]) ^ iArr[i28];
            int i47 = i46 + 1;
            iArr5[i46] = (((iArr[i39] ^ iArr2[i40]) ^ iArr3[i33]) ^ iArr4[i32]) ^ iArr2[i34];
            int i48 = i47 + 1;
            iArr5[i47] = iArr3[i38] ^ (((iArr[i42] ^ iArr2[i43]) ^ iArr3[i30]) ^ iArr4[i28]);
            int i49 = i48 + 1;
            iArr5[i48] = (((iArr2[i45] ^ iArr[i44]) ^ iArr3[i27]) ^ iArr4[i26]) ^ iArr4[i42];
            int i50 = ((((i36 ^ iArr[i33]) ^ iArr2[i35]) ^ iArr3[i32]) ^ iArr4[i34]) ^ iArr3[i26];
            int i51 = i50 >>> 24;
            int i52 = (i50 >> 16) & 255;
            int i53 = (i50 >> 8) & 255;
            int i54 = i50 & 255;
            int i55 = iArr4[i28] ^ ((((i25 ^ iArr[i51]) ^ iArr2[i53]) ^ iArr3[i52]) ^ iArr4[i54]);
            int i56 = i55 >>> 24;
            int i57 = (i55 >> 16) & 255;
            int i58 = (i55 >> 8) & 255;
            int i59 = i55 & 255;
            int i60 = ((((i31 ^ iArr[i59]) ^ iArr2[i58]) ^ iArr3[i57]) ^ iArr4[i56]) ^ iArr[i27];
            int i61 = i60 >>> 24;
            int i62 = (i60 >> 16) & 255;
            int i63 = (i60 >> 8) & 255;
            int i64 = i60 & 255;
            int i65 = ((((i41 ^ iArr[i63]) ^ iArr2[i62]) ^ iArr3[i64]) ^ iArr4[i61]) ^ iArr2[i30];
            int i66 = i65 >>> 24;
            int i67 = (i65 >> 16) & 255;
            int i68 = (i65 >> 8) & 255;
            int i69 = i65 & 255;
            int i70 = i49 + 1;
            iArr5[i49] = (((iArr[i54] ^ iArr2[i53]) ^ iArr3[i66]) ^ iArr4[i67]) ^ iArr[i61];
            int i71 = i70 + 1;
            iArr5[i70] = (((iArr2[i51] ^ iArr[i52]) ^ iArr3[i68]) ^ iArr4[i69]) ^ iArr2[i67];
            int i72 = i71 + 1;
            iArr5[i71] = (((iArr[i59] ^ iArr2[i58]) ^ iArr3[i61]) ^ iArr4[i62]) ^ iArr3[i54];
            int i73 = i72 + 1;
            iArr5[i72] = iArr4[i59] ^ (((iArr[i57] ^ iArr2[i56]) ^ iArr3[i63]) ^ iArr4[i64]);
            int i74 = ((((i50 ^ iArr[i67]) ^ iArr2[i69]) ^ iArr3[i66]) ^ iArr4[i68]) ^ iArr3[i61];
            int i75 = i74 >>> 24;
            int i76 = (i74 >> 16) & 255;
            int i77 = (i74 >> 8) & 255;
            int i78 = i74 & 255;
            int i79 = ((((i60 ^ iArr[i75]) ^ iArr2[i77]) ^ iArr3[i76]) ^ iArr4[i78]) ^ iArr4[i63];
            int i80 = i79 >>> 24;
            int i81 = (i79 >> 16) & 255;
            int i82 = (i79 >> 8) & 255;
            int i83 = i79 & 255;
            int i84 = ((((i65 ^ iArr[i83]) ^ iArr2[i82]) ^ iArr3[i81]) ^ iArr4[i80]) ^ iArr[i62];
            int i85 = i84 >>> 24;
            int i86 = (i84 >> 16) & 255;
            int i87 = (i84 >> 8) & 255;
            int i88 = i84 & 255;
            int i89 = ((((i55 ^ iArr[i87]) ^ iArr2[i86]) ^ iArr3[i88]) ^ iArr4[i85]) ^ iArr2[i64];
            int i90 = i89 >>> 24;
            int i91 = i73 + 1;
            iArr5[i73] = (((iArr[i78] ^ iArr2[i77]) ^ iArr3[i90]) ^ iArr4[(i89 >> 16) & 255]) ^ iArr[i86];
            int i92 = i91 + 1;
            iArr5[i91] = ((iArr3[(i89 >> 8) & 255] ^ (iArr[i76] ^ iArr2[i75])) ^ iArr4[i89 & 255]) ^ iArr2[i90];
            int i93 = i92 + 1;
            iArr5[i92] = (iArr4[i86] ^ ((iArr[i83] ^ iArr2[i82]) ^ iArr3[i85])) ^ iArr3[i77];
            int i94 = i93 + 1;
            iArr5[i93] = (((iArr[i81] ^ iArr2[i80]) ^ iArr3[i87]) ^ iArr4[i88]) ^ iArr4[i82];
            int i95 = iArr3[i75] ^ (((iArr2[i83] ^ (i84 ^ iArr[i81])) ^ iArr3[i80]) ^ iArr4[i82]);
            int i96 = i95 >>> 24;
            int i97 = (i95 >> 16) & 255;
            int i98 = (i95 >> 8) & 255;
            int i99 = i95 & 255;
            int i100 = iArr4[i77] ^ ((((i74 ^ iArr[i96]) ^ iArr2[i98]) ^ iArr3[i97]) ^ iArr4[i99]);
            int i101 = i100 >>> 24;
            int i102 = (i100 >> 16) & 255;
            int i103 = (i100 >> 8) & 255;
            int i104 = i100 & 255;
            int i105 = ((((i79 ^ iArr[i104]) ^ iArr2[i103]) ^ iArr3[i102]) ^ iArr4[i101]) ^ iArr[i76];
            int i106 = i105 >>> 24;
            int i107 = (i105 >> 16) & 255;
            int i108 = (i105 >> 8) & 255;
            int i109 = i105 & 255;
            int i110 = ((((i89 ^ iArr[i108]) ^ iArr2[i107]) ^ iArr3[i109]) ^ iArr4[i106]) ^ iArr2[i78];
            int i111 = i110 >>> 24;
            int i112 = (i110 >> 16) & 255;
            int i113 = (i110 >> 8) & 255;
            int i114 = i110 & 255;
            int i115 = i94 + 1;
            iArr5[i94] = (((iArr[i106] ^ iArr2[i107]) ^ iArr3[i104]) ^ iArr4[i103]) ^ iArr[i99];
            int i116 = i115 + 1;
            iArr5[i115] = ((iArr3[i102] ^ (iArr[i108] ^ iArr2[i109])) ^ iArr4[i101]) ^ iArr2[i104];
            int i117 = i116 + 1;
            iArr5[i116] = (((iArr[i111] ^ iArr2[i112]) ^ iArr3[i99]) ^ iArr4[i98]) ^ iArr3[i106];
            i21 = i117 + 1;
            iArr5[i117] = (((iArr[i113] ^ iArr2[i114]) ^ iArr3[i97]) ^ iArr4[i96]) ^ iArr4[i112];
            mVar = this;
            i20 = i114;
            i16 = i110;
            i8 = i107;
            i19 = i111;
            i18 = i108;
            i10 = i109;
            i2 = i95;
            i17 = i106;
            i6 = i100;
            i11 = i105;
            i13 = i112;
            i14 = i113;
            bArr2 = bArr3;
        }
        byte[] bArr4 = bArr2;
        for (int i118 = 16; i118 < 32; i118++) {
            int[] iArr6 = this.c;
            iArr6[i118] = iArr6[i118] & 31;
        }
        if (bArr4 != bArr) {
            CriticalObject.destroy(bArr4);
        }
    }

    @Override // iaik.security.cipher.AbstractC0030k
    void b() {
        int i = this.a[0];
        int i2 = this.a[1];
        int i3 = this.m - 2;
        int i4 = 0;
        int i5 = 16;
        while (i4 < i3) {
            int[] iArr = this.c;
            int i6 = i4 + 1;
            int i7 = iArr[i4] + i2;
            int i8 = i5 + 1;
            int i9 = iArr[i5];
            int i10 = (i7 >>> (32 - i9)) | (i7 << i9);
            int[] iArr2 = o;
            int i11 = iArr2[i10 >>> 24];
            int[] iArr3 = p;
            int i12 = i11 ^ iArr3[(i10 >> 16) & 255];
            int[] iArr4 = q;
            int i13 = i12 - iArr4[(i10 >> 8) & 255];
            int[] iArr5 = r;
            int i14 = i ^ (i13 + iArr5[i10 & 255]);
            int i15 = i6 + 1;
            int i16 = iArr[i6] ^ i14;
            int i17 = i8 + 1;
            int i18 = iArr[i8];
            int i19 = (i16 << i18) | (i16 >>> (32 - i18));
            int i20 = i2 ^ (iArr5[i19 & 255] ^ ((iArr2[i19 >>> 24] - iArr3[(i19 >> 16) & 255]) + iArr4[(i19 >> 8) & 255]));
            int i21 = i15 + 1;
            int i22 = iArr[i15] - i20;
            int i23 = iArr[i17];
            int i24 = (i22 >>> (32 - i23)) | (i22 << i23);
            int i25 = i14 ^ (((iArr2[i24 >>> 24] + iArr3[(i24 >> 16) & 255]) ^ iArr4[(i24 >> 8) & 255]) - iArr5[i24 & 255]);
            i4 = i21;
            i5 = i17 + 1;
            i2 = i25;
            i = i20;
        }
        if (this.m == 16) {
            int[] iArr6 = this.c;
            int i26 = iArr6[15] + i2;
            int i27 = iArr6[31];
            int i28 = (i26 >>> (32 - i27)) | (i26 << i27);
            int i29 = i2;
            i2 = i ^ (((o[i28 >>> 24] ^ p[(i28 >> 16) & 255]) - q[(i28 >> 8) & 255]) + r[i28 & 255]);
            i = i29;
        }
        this.a[0] = i2;
        this.a[1] = i;
    }

    @Override // iaik.security.cipher.AbstractC0030k
    void c() {
        int i;
        int i2 = this.a[0];
        int i3 = this.a[1];
        if (this.m == 16) {
            int[] iArr = this.c;
            int i4 = iArr[15] + i3;
            int i5 = iArr[31];
            int i6 = (i4 >>> (32 - i5)) | (i4 << i5);
            int i7 = i2 ^ (((o[i6 >>> 24] ^ p[(i6 >> 16) & 255]) - q[(i6 >> 8) & 255]) + r[i6 & 255]);
            i = 14;
            i3 = i7;
            i2 = i3;
        } else {
            i = 11;
        }
        int i8 = i + 16;
        while (i > 0) {
            int[] iArr2 = this.c;
            int i9 = i - 1;
            int i10 = iArr2[i] - i3;
            int i11 = i8 - 1;
            int i12 = iArr2[i8];
            int i13 = (i10 >>> (32 - i12)) | (i10 << i12);
            int[] iArr3 = o;
            int i14 = iArr3[i13 >>> 24];
            int[] iArr4 = p;
            int i15 = i14 + iArr4[(i13 >> 16) & 255];
            int[] iArr5 = q;
            int i16 = i15 ^ iArr5[(i13 >> 8) & 255];
            int[] iArr6 = r;
            int i17 = i2 ^ (i16 - iArr6[i13 & 255]);
            int i18 = i9 - 1;
            int i19 = iArr2[i9] ^ i17;
            int i20 = i11 - 1;
            int i21 = iArr2[i11];
            int i22 = (i19 >>> (32 - i21)) | (i19 << i21);
            int i23 = i3 ^ (iArr6[i22 & 255] ^ ((iArr3[i22 >>> 24] - iArr4[(i22 >> 16) & 255]) + iArr5[(i22 >> 8) & 255]));
            int i24 = i18 - 1;
            int i25 = iArr2[i18] + i23;
            int i26 = iArr2[i20];
            int i27 = (i25 >>> (32 - i26)) | (i25 << i26);
            int i28 = i17 ^ (((iArr3[i27 >>> 24] ^ iArr4[(i27 >> 16) & 255]) - iArr5[(i27 >> 8) & 255]) + iArr6[i27 & 255]);
            i = i24;
            i8 = i20 - 1;
            i3 = i28;
            i2 = i23;
        }
        this.a[0] = i3;
        this.a[1] = i2;
    }

    @Override // iaik.security.cipher.AbstractC0030k
    public void d() {
        super.d();
        this.m = 0;
        CriticalObject.destroy(this.c);
        this.c = null;
    }

    @Override // iaik.security.cipher.t
    AlgorithmParameters e() {
        CAST128ParameterSpec cAST128ParameterSpec = new CAST128ParameterSpec(this.n, this.e);
        try {
            AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("CAST128", "IAIK");
            algorithmParameters.init(cAST128ParameterSpec);
            return algorithmParameters;
        } catch (Exception unused) {
            return null;
        }
    }

    @Override // iaik.security.cipher.AbstractC0030k
    protected void finalize() {
        d();
        super.finalize();
    }
}
