library(energy)
 dcor_DC<-numeric(0)
  for(i in 1:p){
    dcro_DC[i]<-DCOR(X[,i],Y)$dCor
  }
