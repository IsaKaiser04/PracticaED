import {ViewConfig} from '@vaadin/hilla-file-router/types.js';
import {
    Button,
    ComboBox,
    Dialog,
    Grid,
    GridColumn,
    GridItemModel,
    GridSortColumn,
    HorizontalLayout,
    Icon,
    NumberField,
    Select,
    TextField,
    VerticalLayout
} from '@vaadin/react-components';
import {Notification} from '@vaadin/react-components/Notification';

import {useSignal} from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import {Group, ViewToolbar} from 'Frontend/components/ViewToolbar';

import {useDataProvider} from '@vaadin/hilla-react-crud';
import Cancion from 'Frontend/generated/org/unl/music/base/models/Artista';
import {CancionService} from 'Frontend/generated/endpoints';
import {useCallback, useEffect, useState} from 'react';

export const config: ViewConfig = {
  title: 'CancionOP',
  menu: {
    icon: 'vaadin:music',
    order: 1,
    title: 'CancionOP',
  },
};

type CancionEntryFormProps = {
  onCancionCreated?: () => void;
};

type CancionEntryFormPropsUpdate = ()=> {
  arguments: Cancion,
  onCancionUpdated?: () => void;
};
//GUARDAR CANCION
function CancionEntryForm(props: CancionEntryFormProps) {
  const nombre = useSignal('');
  const genero = useSignal('');
  const album = useSignal('');
  const duracion = useSignal('');
  const url = useSignal('');
  const tipo = useSignal('');
  const createCancion = async () => {
    try {
      if (nombre.value.trim().length > 0 && genero.value.trim().length > 0) {
        const id_genero = parseInt(genero.value) +1;
        const id_album = parseInt(album.value) +1;
        await CancionService.createCancion(nombre.value, id_genero, parseInt(duracion.value), url.value, tipo.value, id_album);
        if (props.onCancionCreated) {
          props.onCancionCreated();
        }

        nombre.value = '';
        genero.value = '';
        album.value = '';
        duracion.value = '';
        url.value = '';
        tipo.value = '';
        dialogOpened.value = false;
        Notification.show('Cancion creado', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  let listaGenero = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listaAlbumGenero().then(data =>
        //console.log(data)
        listaGenero.value = data
    );
  }, []);
  let listaAlbum = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listAlbumCombo().then(data =>
        //console.log(data)
        listaAlbum.value = data
    );
  }, []);

  let listaTipo = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listTipo().then(data =>
        //console.log(data)
        listaTipo.value = data
    );
  }, []);
  const dialogOpened = useSignal(false);
  return (
      <>
        <Dialog
            modeless
            headerTitle="Nueva Cancion"
            opened={dialogOpened.value}
            onOpenedChanged={({ detail }) => {
              dialogOpened.value = detail.value;
            }}
            footer={
              <>
                <Button
                    onClick={() => {
                      dialogOpened.value = false;
                    }}
                >
                  Candelar
                </Button>
                <Button onClick={createCancion} theme="primary">
                  Registrar
                </Button>

              </>
            }
        >
          <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
            <TextField label="Nombre del cancion"
                       placeholder="Ingrese el nombre de la cancion"
                       aria-label="Nombre del cancion"
                       value={nombre.value}
                       onValueChanged={(evt) => (nombre.value = evt.detail.value)}
            />
            <ComboBox label="Genero"
                      items={listaGenero.value}
                      placeholder='Seleccione un genero'
                      aria-label='Seleccione un genero de la lista'
                      value={genero.value}
                      onValueChanged={(evt) => (genero.value = evt.detail.value)}
            />
            <ComboBox label="Album"
                      items={listaAlbum.value}
                      placeholder='Seleccione un album'
                      aria-label='Seleccione un album de la lista'
                      value={album.value}
                      onValueChanged={(evt) => (album.value = evt.detail.value)}
            />
            <ComboBox label="Tipo"
                      items={listaTipo.value}
                      placeholder='Seleccione un tipo de archivo'
                      aria-label='Seleccione un tipo de archivo de la lista'
                      value={tipo.value}
                      onValueChanged={(evt) => (tipo.value = evt.detail.value)}
            />
            <NumberField  label="Duracion"

                          placeholder="Ingrese la Duracion de la cancion"
                          aria-label="Nombre la Duracion de la cancion"
                          value={duracion.value}
                          onValueChanged={(evt) => (duracion.value = evt.detail.value)}
            />
            <TextField label="Link de la cancion"
                       placeholder="Ingrese el link de la cancion"
                       aria-label="Nombre el link de la cancion"
                       value={url.value}
                       onValueChanged={(evt) => (url.value = evt.detail.value)}
            />
          </VerticalLayout>
        </Dialog>
        <Button
            onClick={() => {
              dialogOpened.value = true;
            }}
        >
          Agregar
        </Button>
      </>
  );
}

//EDITAR ARTISTA
const CancionEntryFormUpdate= function(props: CancionEntryFormPropsUpdate){

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };

  const nombre = useSignal(props.arguments.nombre);
  const genero = useSignal(props.arguments.genero);
  const album = useSignal(props.arguments.album);
  const duracion = useSignal(props.arguments.duracion);
  const url = useSignal(props.arguments.url);
  const tipo = useSignal(props.arguments.tipo);

  const updateCancion = async () => {
    try {
      if (nombre.value.trim().length > 0 && genero.value.trim().length > 0) {
        const id_genero = parseInt(genero.value) +1;
        const id_album = parseInt(album.value) +1;

        await CancionService.updateCancion(props.arguments.id,nombre.value, id_genero, parseInt(duracion.value), url.value, tipo.value, id_album);
        if (props.onCancionUpdated) {
          props.onCancionUpdated();
        }

        nombre.value = '';
        genero.value = '';
        album.value = '';
        duracion.value = '';
        url.value = '';
        tipo.value = '';
        dialogOpened.value = false;
        Notification.show('Cancion Actualizada', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo actualiza, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };
  let listaGenero = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listaAlbumGenero().then(data =>
        //console.log(data)
        listaGenero.value = data
    );
  }, []);
  let listaAlbum = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listAlbumCombo().then(data =>
        //console.log(data)
        listaAlbum.value = data
    );
  }, []);

  let listaTipo = useSignal<String[]>([]);
  useEffect(() => {
    CancionService.listTipo().then(data =>
        //console.log(data)
        listaTipo.value = data
    );
  }, []);
  const dialogOpened = useSignal(false);

  return (
      <>
        <Dialog
            modeless
            draggable
            headerTitle="Actualizar Cancion"
            opened={dialogOpened.value}
            onOpenedChanged={({ detail }) => {
              dialogOpened.value = detail.value;
            }}
            header={
          <h2
              className="draggable"
              style={{
                flex: 1,
                cursor: 'move',
                margin: 0,
                fontSize: '1.5em',
                fontWeight: 'bold',
                padding: 'var(--lumo-space-m) 0',
              }}
          >
            Editar Banda
          </h2>
        }
            footerRenderer={() => (
                <>
                  <Button onClick={close}>Cancelar</Button>
                  <Button theme="primary" onClick={updateCancion}>
                    Actualizar
                  </Button>
                </>
            )}
        >
          <VerticalLayout style={{ alignItems: 'stretch', width: '18rem', maxWidth: '100%' }}>
            <TextField label="Nombre del cancion"
                       placeholder="Ingrese el nombre de la cancion"
                       aria-label="Nombre del cancion"
                       value={nombre.value}
                       onValueChanged={(evt) => (nombre.value = evt.detail.value)}
            />
            <ComboBox label="Genero"
                      items={listaGenero.value}
                      placeholder='Seleccione un genero'
                      aria-label='Seleccione un genero de la lista'
                      value={genero.value}
                      onValueChanged={(evt) => (genero.value = evt.detail.value)}
            />
            <ComboBox label="Album"
                      items={listaAlbum.value}
                      placeholder='Seleccione un album'
                      aria-label='Seleccione un album de la lista'
                      value={album.value}
                      onValueChanged={(evt) => (album.value = evt.detail.value)}
            />
            <ComboBox label="Tipo"
                      items={listaTipo.value}
                      placeholder='Seleccione un tipo de archivo'
                      aria-label='Seleccione un tipo de archivo de la lista'
                      value={tipo.value}
                      onValueChanged={(evt) => (tipo.value = evt.detail.value)}
            />
            <NumberField  label="Duracion"

                          placeholder="Ingrese la Duracion de la cancion"
                          aria-label="Nombre la Duracion de la cancion"
                          value={duracion.value}
                          onValueChanged={(evt) => (duracion.value = evt.detail.value)}
            />
            <TextField label="Link de la cancion"
                       placeholder="Ingrese el link de la cancion"
                       aria-label="Nombre el link de la cancion"
                       value={url.value}
                       onValueChanged={(evt) => (url.value = evt.detail.value)}
            />
          </VerticalLayout>
        </Dialog>
        <Button
            onClick={() => {
              dialogOpened.value = true;
            }}
        >
          Actualizar
        </Button>
      </>
  );
};


//LISTA DE ARTISTAS
export default function CancionListView() {

  const [items, setItems] = useState([]);
  useEffect(() => {
    CancionService.listAll().then(function (data) {
      //items.values = data;
      setItems(data);
    });
  }, []);

  const loadData = useCallback(() => {
    CancionService.listAll().then(data => {
      setItems(data);
    });
  }, []);

  const dataProvider = useDataProvider<Cancion>({
    list: () => CancionService.listAll(),
  });

  const order = (event, columnId) => {
    console.log(event);
    const direction = event.detail.value;
    // Custom logic based on the sorting direction
    console.log(`Sort direction changed for column ${columnId} to ${direction}`);

    var dir = (direction == 'asc') ? 1 : 2;
    CancionService.order(columnId, dir).then(function (data) {
      setItems(data);
    });
  }

  const search = async () => {
    try {
      console.log(criterio.value + " " + texto.value);
      CancionService.searchOp(criterio.value, texto.value, 0).then(function (data) {
        setItems(data);
      });

      criterio.value = '';
      texto.value = '';

      Notification.show('Busqueda realizada', { duration: 5000, position: 'bottom-end', theme: 'success' });

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };


  const criterio = useSignal('');
  const texto = useSignal('');
  const itemSelect = [
    {
      label: 'Genero',
      value: 'genero',
    },
    {
      label: 'Album',
      value: 'album',
    },
    {
      label: 'Nombre',
      value: 'nombre'

    }
  ];

  function indexIndex({model}:{model:GridItemModel<Cancion>}) {
    return (
        <span>
        {model.index + 1}
      </span>
    );
  }


  function indexLink({ item }: { item: Cancion }) {
    return (
        <span>
      <CancionEntryFormUpdate arguments={item} onCancionUpdated={loadData} />
    </span>
    );
  }

  return (
      <main className="w-full h-full flex flex-col box-border gap-s p-m">
        <ViewToolbar title="Lista Canciones">
          <Group>
            <CancionEntryForm onCancionCreated={loadData}/>
          </Group>
        </ViewToolbar>
        <HorizontalLayout theme="spacing">
          <Select items={itemSelect}
                  value={criterio.value}
                  onValueChanged={(evt) => (criterio.value = evt.detail.value)}
                  placeholder="Selecione un criterio">
          </Select>

          <TextField
              placeholder="Search"
              style={{ width: '50%' }}
              value={texto.value}
              onValueChanged={(evt) => (texto.value = evt.detail.value)}
          >
            <Icon slot="prefix" icon="vaadin:search" />
          </TextField>
          <Button onClick={search} theme="primary">
            BUSCAR
          </Button>
        </HorizontalLayout>
        <Grid items={items}>
          <GridColumn header="Nro" renderer={indexIndex} />
          <GridSortColumn path="nombre" header="Nombre" onDirectionChanged={(e) => order(e, "nombre")} />
          <GridSortColumn path="genero" header="Genero" onDirectionChanged={(e) => order(e, "genero")} />
          <GridSortColumn path="url" header="URL" onDirectionChanged={(e) => order(e, "url")} />
          <GridSortColumn path="album" header="Album" onDirectionChanged={(e) => order(e, "album")} />
          <GridSortColumn path="duracion" header="Duracion" onDirectionChanged={(e) => order(e, "duracion")} />
          <GridSortColumn path="tipo" header="Tipo" onDirectionChanged={(e) => order(e, "tipo")} />
          <GridColumn header="Acciones" renderer={indexLink} />

        </Grid>
      </main>
  );
}

