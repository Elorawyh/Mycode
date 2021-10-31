%Ä£ºý£º
function Img_out=Blur(Img_in,a,b,T)
Img_in=double(imread('lena.bmp'));
Img_fft_shift=fftshift(fft2(Img_in));
[M,N]=size(Img_fft_shift);
for i=1:M
    for j=1:N
        H(i,j)=(T/(pi*(i*a+j*b)))*sin(pi*(i*a+j*b))*exp(-sqrt(-1)*pi*(i*a+j*b));
        G(i,j)=H(i,j)*Img_fft_shift(i,j);
    end
end
Img_out=ifft2(ifftshift(G));
Img_out=256.*Img_out./max(max(Img_out));
Img_out=uint8(real(Img_out));
end