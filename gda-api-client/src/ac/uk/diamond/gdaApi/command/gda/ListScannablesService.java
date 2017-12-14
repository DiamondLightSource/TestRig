package ac.uk.diamond.gdaApi.command.gda;

import ac.uk.diamond.gdaApi.command.io.CommandModelRunner;

import java.util.List;

public interface ListScannablesService<TOutput> {
    TOutput collectList();
}
