package pereira.lopes.julio.halisson.galeirapublica;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.concurrent.Executors;

public class GalleryPagingSource {

    GalleryRepository galleryRepository;
    Integer initialLoadSize = 0;

    public GalleryPagingSource(GalleryRepository galleryRepository) {
        this.galleryRepository = galleryRepository;
    }

    @Nullable
    @Override
    public Integer getRefreshKey(@NonNull PagingState<Integer, ImageData> pagingState) {
        // O método getRefreshKey determina a chave usada para recarregar os dados.
        // Neste caso, não estamos implementando recarregamento, então retornamos null.
        return null;
    }

    @NonNull
    @Override
    public ListenableFuture<LoadResult<Integer, ImageData>> loadFuture(@NonNull LoadParams<Integer> loadParams) {
        // Este método é responsável por carregar os dados de forma assíncrona com base nos parâmetros de carregamento.

        Integer nextPageNumber = loadParams.getKey();
        if (nextPageNumber == null) {
            nextPageNumber = 1;
            initialLoadSize = loadParams.getLoadSize();
        }

        Integer offSet = 0;
        if (nextPageNumber == 2) {
            offSet = initialLoadSize;
        } else {
            offSet = ((nextPageNumber - 1) * loadParams.getLoadSize()) + (initialLoadSize - loadParams.getLoadSize());
        }

        // Configurando um serviço executor para execução assíncrona.
        ListeningExecutorService service = MoreExecutors.listeningDecorator(Executors.newSingleThreadExecutor());
        Integer finalOffSet = offSet;
        Integer finalNextPageNumber = nextPageNumber;

        // Submetendo a carga de dados a um serviço executor para execução assíncrona.
        ListenableFuture<LoadResult<Integer, ImageData>> lf = service.submit(new Callable<LoadResult<Integer, ImageData>>() {
            @Override
            public LoadResult<Integer, ImageData> call() {
                List<ImageData> imageDataList = null;
                try {
                    // Carregando os dados da galeria usando o repositório.
                    imageDataList = galleryRepository.loadImageData(loadParams.getLoadSize(), finalOffSet);

                    // Determinando a chave para a próxima página se houver mais dados disponíveis.
                    Integer nextKey = null;
                    if (imageDataList.size() >= loadParams.getLoadSize()) {
                        nextKey = finalNextPageNumber + 1;
                    }

                    // Retornando os resultados do carregamento.
                    return new LoadResult.Page<Integer, ImageData>(imageDataList, null, nextKey);
                } catch (FileNotFoundException e) {
                    // Em caso de erro, retornando um resultado de erro.
                    return new LoadResult.Error<>(e);
                }
            }
        });

        // Retornando o resultado futuro do carregamento.
        return lf;
    }
}
